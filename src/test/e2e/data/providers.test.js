const request = require('../request');

test('GET /v1/data/providers', async () => {
    const response = await request.get('/v1/data/providers');

    expect(response.status).toBe(200);
    expect(response.body.length > 0).toBe(true);
});

test('GET /v1/data/providers/insee', async () => {
    const response = await request.get('/v1/data/providers/insee');

    expect(response.status).toBe(200);
    const expectedData = {
        id: "insee",
        name: "Insee",
        url: "https://www.insee.fr",
        description: "Institut national de la statistique et des études économiques"
    };
    expect(response.body).toEqual(expectedData);
});
