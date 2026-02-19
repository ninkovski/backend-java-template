CREATE TABLE IF NOT EXISTS profile (
  id INT PRIMARY KEY,
  full_name VARCHAR(255),
  role VARCHAR(255),
  summary CLOB,
  linkedin_url VARCHAR(512),
  github_url VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS experiences (
  id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT,
  company VARCHAR(255),
  position VARCHAR(255),
  start_date VARCHAR(100),
  end_date VARCHAR(100),
  CONSTRAINT fk_profile_experience FOREIGN KEY(profile_id) REFERENCES profile(id)
);

CREATE TABLE IF NOT EXISTS experience_highlights (
  id INT PRIMARY KEY AUTO_INCREMENT,
  experience_id INT,
  highlight_text CLOB,
  CONSTRAINT fk_experience_highlight FOREIGN KEY(experience_id) REFERENCES experiences(id)
);

CREATE TABLE IF NOT EXISTS skills (
  id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT,
  name VARCHAR(255),
  level VARCHAR(100),
  years INT,
  CONSTRAINT fk_profile_skill FOREIGN KEY(profile_id) REFERENCES profile(id)
);

CREATE TABLE IF NOT EXISTS certifications (
  id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT,
  name VARCHAR(255),
  provider VARCHAR(255),
  credential_url VARCHAR(1024),
  CONSTRAINT fk_profile_certification FOREIGN KEY(profile_id) REFERENCES profile(id)
);
