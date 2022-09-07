const request = require('../request');

test('GET /v1/regions', async () => {
    const response = await request.get('/v1/regions');

    expect(response.status).toBe(200);
    expect(response.body.length).toBe(18);
});

test('GET /v1/regions/52', async () => {
    const response = await request.get('/v1/regions/52');

    expect(response.status).toBe(200);
    const expectedData = {
        code: "52",
        libelle: "Pays de la Loire"
    };
    expect(response.body).toEqual(expectedData);
});

test('GET /v1/regions/9999', async () => {
    const response = await request.get('/v1/regions/9999');

    expect(response.status).toBe(404);
    const expectedData = { message: "Not Found" };
    expect(response.body).toEqual(expectedData);
});
