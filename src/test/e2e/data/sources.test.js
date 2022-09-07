const request = require('../request');

test('GET /v1/data/sources', async () => {
    const response = await request.get('/v1/data/sources');

    expect(response.status).toBe(200);
    expect(response.body.length > 0).toBe(true);
});

test('GET /v1/data/sources/insee-cog', async () => {
    const response = await request.get('/v1/data/sources/insee-cog');

    expect(response.status).toBe(200);
    const expectedData = {
        provider: {
            id: "insee",
            name: "Insee",
            url: "https://www.insee.fr",
            description: "Institut national de la statistique et des études économiques"
        },
        id: "insee-cog",
        name: "Code officiel géographique de l'Insee",
        url: "https://www.insee.fr/fr/information/5057840",
        description: "Code officiel géographique au 1er janvier 2021",
        publication_date: "2021-01-01"
    };
    expect(response.body).toEqual(expectedData);
});
