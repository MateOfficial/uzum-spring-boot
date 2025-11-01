# verify.ps1 - simple PowerShell verifier for the app (local H2 run)
# Usage: ./verify.ps1

$jar = "build\libs\uzum-java-student-0.0.1-SNAPSHOT.jar"
if (-not (Test-Path $jar)) {
    Write-Output "Jar not found, building..."
    & .\gradlew.bat bootJar
    if ($LASTEXITCODE -ne 0) { Write-Error "Build failed"; exit 2 }
}

# Если приложение уже доступно на /health, ничего не стартуем — просто проверим
$startedByScript = $false
Write-Output "Проверяем, запущено ли приложение..."
try {
    $r = Invoke-RestMethod -Method Get -Uri http://localhost:8080/health -TimeoutSec 2 -ErrorAction Stop
    if ($r) {
        Write-Output "Приложение уже запущено, пропускаем старт (используем существующий процесс)."
        $startedByScript = $false
    }
} catch {
    # Не доступно — стартуем
    Write-Output "Приложение не найдено, стартуем jar..."
    # Use .NET ProcessStartInfo to start java so we avoid Start-Process/Start-Job quirks
    $psi = New-Object System.Diagnostics.ProcessStartInfo
    $psi.FileName = "java"
    $psi.Arguments = "-jar `"$jar`" --spring.datasource.url=jdbc:h2:mem:testdb"
    $psi.RedirectStandardOutput = $true
    $psi.RedirectStandardError = $true
    $psi.UseShellExecute = $false
    $proc = New-Object System.Diagnostics.Process
    $proc.StartInfo = $psi
    try {
        $proc.Start() | Out-Null
        $startedByScript = $true
        Write-Output "Запущен процесс java, pid=$($proc.Id)"
    } catch {
        Write-Error "Failed to start java process: $_"
        exit 4
    }
}

# wait for app to boot (timeout)
$timeout = 60
$start = Get-Date
$up = $false
while ((Get-Date) - $start -lt (New-TimeSpan -Seconds $timeout)) {
    try {
        $resp = Invoke-RestMethod -Method Get -Uri http://localhost:8080/health -TimeoutSec 2
        if ($resp -ne $null) { $up = $true; break }
    } catch { Start-Sleep -Seconds 1 }
}

if (-not $up) {
    Write-Error "Application did not start within $timeout seconds"
    if ($proc -and $proc.Id) { Stop-Process -Id $proc.Id -ErrorAction SilentlyContinue }
    exit 3
}

Write-Output "Application is up, running checks..."
$ok = $true
try {
    # create person
    $person = @{ id = 100; name = 'Verify User'; birthdate = '01.01.1990' } | ConvertTo-Json
    $p = Invoke-RestMethod -Method Post -Uri http://localhost:8080/person -Body $person -ContentType 'application/json' -ErrorAction Stop
    Write-Output "POST /person -> OK"
} catch {
    Write-Error "POST /person failed: $_"
    $ok = $false
}

try {
    # create car
    $car = @{ id = 200; model = 'Verify-V1'; horsepower = 150; ownerId = 100 } | ConvertTo-Json
    $c = Invoke-RestMethod -Method Post -Uri http://localhost:8080/car -Body $car -ContentType 'application/json' -ErrorAction Stop
    Write-Output "POST /car -> OK"
} catch {
    Write-Error "POST /car failed: $_"
    $ok = $false
}

try {
    $pw = Invoke-RestMethod -Method Get -Uri 'http://localhost:8080/personwithcars?personid=100' -ErrorAction Stop
    if ($pw -and $pw.id -eq 100) { Write-Output "GET /personwithcars -> OK" } else { Write-Error "GET /personwithcars -> unexpected body"; $ok = $false }
} catch {
    Write-Error "GET /personwithcars failed: $_"; $ok = $false
}

try {
    $stats = Invoke-RestMethod -Method Get -Uri 'http://localhost:8080/statistics' -ErrorAction Stop
    Write-Output "GET /statistics -> OK"; Write-Output $stats | Out-String
} catch {
    Write-Error "GET /statistics failed: $_"; $ok = $false
}

try {
    Invoke-RestMethod -Method Get -Uri 'http://localhost:8080/clear' -ErrorAction Stop
    Write-Output "GET /clear -> OK"
} catch {
    Write-Error "GET /clear failed: $_"; $ok = $false
}

# stop app
if ($proc -and $proc.Id) {
    Stop-Process -Id $proc.Id -ErrorAction SilentlyContinue
}

if ($ok) { Write-Output "VERIFY: PASS"; exit 0 } else { Write-Error "VERIFY: FAIL"; exit 1 }
