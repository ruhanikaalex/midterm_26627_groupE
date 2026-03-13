-- 1. Locations
INSERT INTO locations (id, name, location_type, parent_id) VALUES (1, 'Kigali City', 'PROVINCE', NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (2, 'Nyarugenge', 'DISTRICT', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (3, 'Nyamirambo', 'SECTOR', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (4, 'Rwezamenyo', 'SECTOR', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (5, 'Nyakabanda', 'SECTOR', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (6, 'Gasharu', 'CELL', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (7, 'Village 1', 'VILLAGE', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (8, 'Village 2', 'VILLAGE', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (9, 'Northern Province', 'PROVINCE', NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO locations (id, name, location_type, parent_id) VALUES (10, 'Musanze', 'DISTRICT', 9) ON CONFLICT (id) DO NOTHING;

-- 2. Users
INSERT INTO users (id, username, password, email, role, location_id) VALUES (1, 'alex_instructor', 'pass123', 'alex@auca.ac.rw', 'INSTRUCTOR', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO users (id, username, password, email, role, location_id) VALUES (2, 'john_student', 'pass123', 'john@student.com', 'STUDENT', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO users (id, username, password, email, role, location_id) VALUES (3, 'jane_student', 'pass123', 'jane@student.com', 'STUDENT', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO users (id, username, password, email, role, location_id) VALUES (4, 'admin_user', 'admin123', 'admin@quiz.com', 'ADMIN', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO users (id, username, password, email, role, location_id) VALUES (5, 'eric_instructor', 'pass123', 'eric@auca.ac.rw', 'INSTRUCTOR', 10) ON CONFLICT (id) DO NOTHING;

-- 3. Quizzes
INSERT INTO quizzes (id, title, status, created_by, duration_minutes) VALUES (1, 'Java Basics', 'PUBLISHED', 1, 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO quizzes (id, title, status, created_by, duration_minutes) VALUES (2, 'Database Management', 'PUBLISHED', 1, 45) ON CONFLICT (id) DO NOTHING;
INSERT INTO quizzes (id, title, status, created_by, duration_minutes) VALUES (3, 'Web Programming', 'PUBLISHED', 5, 60) ON CONFLICT (id) DO NOTHING;
INSERT INTO quizzes (id, title, status, created_by, duration_minutes) VALUES (4, 'Software Engineering', 'DRAFT', 5, 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO quizzes (id, title, status, created_by, duration_minutes) VALUES (5, 'Networking Essentials', 'PUBLISHED', 1, 40) ON CONFLICT (id) DO NOTHING;

-- 4. Questions
INSERT INTO questions (id, text, type, category) VALUES (1, 'What is JVM?', 'SINGLE_CHOICE', 'Java') ON CONFLICT (id) DO NOTHING;
INSERT INTO questions (id, text, type, category) VALUES (2, 'Select Java features:', 'MULTIPLE_CHOICE', 'Java') ON CONFLICT (id) DO NOTHING;
INSERT INTO questions (id, text, type, category) VALUES (3, 'What does SQL stand for?', 'SINGLE_CHOICE', 'Database') ON CONFLICT (id) DO NOTHING;
INSERT INTO questions (id, text, type, category) VALUES (4, 'Is HTML a programming language?', 'TRUE_FALSE', 'Web') ON CONFLICT (id) DO NOTHING;
INSERT INTO questions (id, text, type, category) VALUES (5, 'Name a popular Java framework.', 'SHORT_ANSWER', 'Java') ON CONFLICT (id) DO NOTHING;

-- 5. Options
INSERT INTO options (id, text, correct, question_id) VALUES (1, 'Java Virtual Machine', true, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (2, 'Java Visual Maker', false, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (3, 'Platform Independent', true, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (4, 'Object Oriented', true, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (5, 'Structured Query Language', true, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (6, 'Strong Query Language', false, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (7, 'True', false, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO options (id, text, correct, question_id) VALUES (8, 'False', true, 4) ON CONFLICT (id) DO NOTHING;

-- 6. Quiz Questions (Many-to-Many)
INSERT INTO quiz_questions (quiz_id, question_id) VALUES (1, 1) ON CONFLICT DO NOTHING;
INSERT INTO quiz_questions (quiz_id, question_id) VALUES (1, 2) ON CONFLICT DO NOTHING;
INSERT INTO quiz_questions (quiz_id, question_id) VALUES (2, 3) ON CONFLICT DO NOTHING;
INSERT INTO quiz_questions (quiz_id, question_id) VALUES (3, 4) ON CONFLICT DO NOTHING;
INSERT INTO quiz_questions (quiz_id, question_id) VALUES (1, 5) ON CONFLICT DO NOTHING;

-- 7. Quiz Attempts
INSERT INTO quiz_attempts (id, user_id, quiz_id, status, score) VALUES (1, 2, 1, 'SUBMITTED', 80.0) ON CONFLICT (id) DO NOTHING;
INSERT INTO quiz_attempts (id, user_id, quiz_id, status, score) VALUES (2, 3, 1, 'SUBMITTED', 100.0) ON CONFLICT (id) DO NOTHING;
INSERT INTO quiz_attempts (id, user_id, quiz_id, status, score) VALUES (3, 2, 2, 'SUBMITTED', 90.0) ON CONFLICT (id) DO NOTHING;
INSERT INTO quiz_attempts (id, user_id, quiz_id, status, score) VALUES (4, 3, 3, 'SUBMITTED', 75.0) ON CONFLICT (id) DO NOTHING;
INSERT INTO quiz_attempts (id, user_id, quiz_id, status, score) VALUES (5, 2, 3, 'SUBMITTED', 85.0) ON CONFLICT (id) DO NOTHING;

-- 8. Answers
INSERT INTO answers (id, attempt_id, question_id, option_id, text_answer) VALUES (1, 1, 1, 1, NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO answers (id, attempt_id, question_id, option_id, text_answer) VALUES (2, 2, 1, 1, NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO answers (id, attempt_id, question_id, option_id, text_answer) VALUES (3, 3, 3, 5, NULL) ON CONFLICT (id) DO NOTHING;
INSERT INTO answers (id, attempt_id, question_id, option_id, text_answer) VALUES (4, 1, 5, NULL, 'Spring') ON CONFLICT (id) DO NOTHING;
INSERT INTO answers (id, attempt_id, question_id, option_id, text_answer) VALUES (5, 4, 4, 8, NULL) ON CONFLICT (id) DO NOTHING;

-- 9. Notifications
INSERT INTO notifications (id, user_id, message, read) VALUES (1, 2, 'Quiz 1 is now available', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO notifications (id, user_id, message, read) VALUES (2, 3, 'Quiz 1 is now available', true) ON CONFLICT (id) DO NOTHING;
INSERT INTO notifications (id, user_id, message, read) VALUES (3, 2, 'Your score for Quiz 1 is 80.0', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO notifications (id, user_id, message, read) VALUES (4, 4, 'New user registered: student1', true) ON CONFLICT (id) DO NOTHING;
INSERT INTO notifications (id, user_id, message, read) VALUES (5, 5, 'Quiz 4 is still in draft', false) ON CONFLICT (id) DO NOTHING;

-- 10. Reports
INSERT INTO reports (id, type, generated_by, quiz_id, attempt_id, total_score, note) VALUES (1, 'SCORE_SUMMARY', 1, 1, 1, 80.0, 'Good attempt') ON CONFLICT (id) DO NOTHING;
INSERT INTO reports (id, type, generated_by, quiz_id, attempt_id, total_score, note) VALUES (2, 'SCORE_SUMMARY', 1, 1, 2, 100.0, 'Perfect score!') ON CONFLICT (id) DO NOTHING;
INSERT INTO reports (id, type, generated_by, quiz_id, attempt_id, total_score, note) VALUES (3, 'WEEKLY', 4, NULL, NULL, NULL, 'Weekly system report') ON CONFLICT (id) DO NOTHING;
INSERT INTO reports (id, type, generated_by, quiz_id, attempt_id, total_score, note) VALUES (4, 'DAILY', 4, NULL, NULL, NULL, 'Daily activity report') ON CONFLICT (id) DO NOTHING;
INSERT INTO reports (id, type, generated_by, quiz_id, attempt_id, total_score, note) VALUES (5, 'SCORE_SUMMARY', 1, 2, 3, 90.0, 'Very good') ON CONFLICT (id) DO NOTHING;

-- Sync sequences for Auto-increment IDs
SELECT setval(pg_get_serial_sequence('locations', 'id'), (SELECT MAX(id) FROM locations));
SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));
SELECT setval(pg_get_serial_sequence('quizzes', 'id'), (SELECT MAX(id) FROM quizzes));
SELECT setval(pg_get_serial_sequence('questions', 'id'), (SELECT MAX(id) FROM questions));
SELECT setval(pg_get_serial_sequence('options', 'id'), (SELECT MAX(id) FROM options));
SELECT setval(pg_get_serial_sequence('quiz_attempts', 'id'), (SELECT MAX(id) FROM quiz_attempts));
SELECT setval(pg_get_serial_sequence('answers', 'id'), (SELECT MAX(id) FROM answers));
SELECT setval(pg_get_serial_sequence('notifications', 'id'), (SELECT MAX(id) FROM notifications));
SELECT setval(pg_get_serial_sequence('reports', 'id'), (SELECT MAX(id) FROM reports));
