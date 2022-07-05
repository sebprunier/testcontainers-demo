-- Fournisseurs de données
CREATE TABLE Data_Provider
(
    id          character varying(50) PRIMARY KEY,
    name        character varying(100) NOT NULL,
    url         character varying(200),
    description text
);

COMMENT ON TABLE Data_Provider IS 'Fournisseurs de données';

COMMENT ON COLUMN Data_Provider.id IS 'Identifiant unique du fournisseur de données';
COMMENT ON COLUMN Data_Provider.name IS 'Nom du fournisseur de données';
COMMENT ON COLUMN Data_Provider.description IS 'Description du fournisseur de données';
COMMENT ON COLUMN Data_Provider.url IS 'URL du fournisseur de données';

INSERT INTO Data_Provider(id, name, url, description)
VALUES ('insee',
        'Insee',
        'https://www.insee.fr',
        'Institut national de la statistique et des études économiques');

INSERT INTO Data_Provider(id, name, url, description)
VALUES ('ign',
        'IGN',
        'https://www.ign.fr/',
        'Institut National de l''Information Géographique et Forestière');

INSERT INTO Data_Provider(id, name, url, description)
VALUES ('ministere-de-la-transition-ecologique',
        'Ministère de la Transition écologique',
        'https://www.ecologie.gouv.fr/',
        NULL);

-- Sources de données
CREATE TABLE Data_Source
(
    id               character varying(50) PRIMARY KEY,
    name             character varying(100) NOT NULL,
    url              character varying(200),
    description      text,
    publication_date date,
    data_provider_id character varying(50)  NOT NULL REFERENCES Data_Provider (id)
);

COMMENT ON TABLE Data_Source IS 'Sources de données';

COMMENT ON COLUMN Data_Source.id IS 'Identifiant unique de la source de données';
COMMENT ON COLUMN Data_Source.name IS 'Nom de la source de données';
COMMENT ON COLUMN Data_Source.url IS 'URL de la source de données';
COMMENT ON COLUMN Data_Source.description IS 'Description de la source de données';
COMMENT ON COLUMN Data_Source.publication_date IS 'Date de publication de la source de données';
COMMENT ON COLUMN Data_Source.data_provider_id IS 'Identifiant du fournisseur de données';

INSERT INTO Data_Source(id, name, url, description, publication_date, data_provider_id)
VALUES ('insee-cog',
        'Code officiel géographique de l''Insee',
        'https://www.insee.fr/fr/information/5057840',
        'Code officiel géographique au 1er janvier 2021',
        '2021-01-01',
        'insee');

INSERT INTO Data_Source(id, name, url, description, publication_date, data_provider_id)
VALUES ('ign-admin-express',
        'ADMIN EXPRESS',
        'https://www.data.gouv.fr/fr/datasets/admin-express/',
        'Le produit ADMIN EXPRESS décrit le découpage administratif du territoire métropolitain et ultra-marin',
        '2021-03-29',
        'ign');

INSERT INTO Data_Source(id, name, url, description, publication_date, data_provider_id)
VALUES ('georisques',
        'Géorisques',
        'https://www.georisques.gouv.fr/donnees/bases-de-donnees',
        'Géorisques est le site de référence sur les risques majeurs naturels et technologiques',
        '2020-09-01',
        'ministere-de-la-transition-ecologique');

INSERT INTO Data_Source(id, name, url, description, publication_date, data_provider_id)
VALUES ('gaspar',
        'GASPAR',
        'https://www.data.gouv.fr/fr/datasets/base-nationale-de-gestion-assistee-des-procedures-administratives-relatives-aux-risques-gaspar/',
        'Base nationale de Gestion ASsistée des Procédures Administratives relatives aux Risques',
        '2020-09-08',
        'ministere-de-la-transition-ecologique');

-- Liens entre les tables et les sources de données
CREATE TABLE Table_Data_Source
(
    table_name text PRIMARY KEY,
    data_source_id character varying(50)  NOT NULL REFERENCES Data_Source (id)
);

COMMENT ON TABLE Table_Data_Source IS 'Liens entre les tables et les sources de données';

COMMENT ON COLUMN Table_Data_Source.table_name IS 'Nom de la table';
COMMENT ON COLUMN Table_Data_Source.data_source_id IS 'Identifiant de la source de données';
