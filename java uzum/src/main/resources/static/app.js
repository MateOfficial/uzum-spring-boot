const json = (o) => JSON.stringify(o, null, 2);

async function postJson(url, body) {
  const res = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });
  const text = await res.text();
  if (!res.ok) throw { status: res.status, body: text };
  return text || null;
}

async function getJson(url) {
  const res = await fetch(url, { method: 'GET' });
  const text = await res.text();
  try { return JSON.parse(text); } catch (e) { return text; }
}

document.addEventListener('DOMContentLoaded', () => {
  const personForm = document.getElementById('personForm');
  const carForm = document.getElementById('carForm');

  personForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const fd = new FormData(personForm);
    const body = { id: Number(fd.get('id')), name: fd.get('name'), birthdate: fd.get('birthdate') };
    const out = document.getElementById('personResult');
    out.textContent = '...sending';
    try {
      await postJson('/person', body);
      out.textContent = 'OK';
    } catch (err) {
      out.textContent = 'ERROR ' + JSON.stringify(err);
    }
  });

  carForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const fd = new FormData(carForm);
    const body = { id: Number(fd.get('id')), model: fd.get('model'), horsepower: Number(fd.get('horsepower')), ownerId: Number(fd.get('ownerId')) };
    const out = document.getElementById('carResult');
    out.textContent = '...sending';
    try {
      await postJson('/car', body);
      out.textContent = 'OK';
    } catch (err) {
      out.textContent = 'ERROR ' + JSON.stringify(err);
    }
  });

  document.getElementById('btnGetPersonWithCars').addEventListener('click', async () => {
    const id = document.getElementById('personIdQuery').value;
    const out = document.getElementById('personWithCarsResult');
    out.textContent = '...loading';
    try {
      const resp = await getJson(`/personwithcars?personid=${encodeURIComponent(id)}`);
      out.textContent = json(resp);
    } catch (err) {
      out.textContent = 'ERROR ' + JSON.stringify(err);
    }
  });

  document.getElementById('btnStats').addEventListener('click', async () => {
    const out = document.getElementById('statsResult');
    out.textContent = '...loading';
    try {
      const resp = await getJson('/statistics');
      out.textContent = json(resp);
    } catch (err) {
      out.textContent = 'ERROR ' + JSON.stringify(err);
    }
  });

  document.getElementById('btnClear').addEventListener('click', async () => {
    const out = document.getElementById('clearResult');
    out.textContent = '...calling';
    try {
      await getJson('/clear');
      out.textContent = 'OK';
    } catch (err) {
      out.textContent = 'ERROR ' + JSON.stringify(err);
    }
  });
});
