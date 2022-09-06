DELETE FROM Table_Data_Source WHERE table_name = 'Region_Contour';
DROP TABLE Region_Contour;

DELETE FROM Table_Data_Source WHERE table_name = 'Region';
DROP TABLE Region;

DROP EXTENSION postgis;
