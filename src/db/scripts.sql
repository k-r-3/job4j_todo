CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    descr text,
    created timestamp DEFAULT current_timestamp,
    done boolean DEFAULT false
)