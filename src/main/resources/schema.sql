CREATE TABLE job_application (
    id              SERIAL PRIMARY KEY,
    position_title  VARCHAR(255) NOT NULL,
    company_name    VARCHAR(255) NOT NULL,
    date_applied    DATE NOT NULL,
    status          VARCHAR(50) NOT NULL,
    job_link        VARCHAR(500),
    notes           TEXT
);