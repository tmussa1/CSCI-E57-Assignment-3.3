CREATE TABLE abtesting(
  service_name    TEXT PRIMARY KEY NOT NULL,
  active          TEXT NOT NULL,
  endpoint        TEXT NOT NULL,
  weight          TEXT NOT NULL);


INSERT INTO abtesting ( service_name, active, endpoint, weight) VALUES ('company-service', 'Y',
'http://company-service-new:8082' , 5);

