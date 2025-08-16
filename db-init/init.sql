-- Create the table (if not created by JPA yet)
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);

-- Insert some sample data
INSERT INTO book (title, author) VALUES
  ('Spring Boot in Action', 'Craig Walls'),
  ('Docker Made Easy', 'Alice Johnson'),
  ('Postgres Basics', 'Bob Smith');
