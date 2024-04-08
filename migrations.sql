CREATE DATABASE jukebox;

USE jukebox;

CREATE TABLE band (
  name VARCHAR(256) PRIMARY KEY,
  creation_date DATE,
  gender VARCHAR(256)
);

CREATE TABLE music (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(256),
  link VARCHAR(256),
  duration INTEGER,
  band_name VARCHAR(256),
  CONSTRAINT fk_music_band_name
  FOREIGN KEY (band_name) REFERENCES band(name) 
  ON UPDATE CASCADE 
  ON DELETE CASCADE
);