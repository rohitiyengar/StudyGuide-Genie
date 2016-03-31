
-- tables
-- Table Actions
CREATE TABLE Actions (
    action_value varchar(20)  NOT NULL,
    action_id SERIAL  NOT NULL,
    CONSTRAINT Actions_pk PRIMARY KEY (action_id)
);

-- Table Content
CREATE TABLE Content (
    course_id integer  NOT NULL,
    topic_id integer  NOT NULL,
    CONSTRAINT Content_pk PRIMARY KEY (course_id,topic_id)
);

-- Table Course
CREATE TABLE Course (
    course_id SERIAL  NOT NULL,
    course_title varchar(50)  NOT NULL,
    CONSTRAINT Course_pk PRIMARY KEY (course_id)
);

-- Table CoursePlan
CREATE TABLE CoursePlan (
    course_id integer  NOT NULL,
    semester varchar(10)  NOT NULL,
    exam_id integer  NOT NULL,
    start_date date  NOT NULL,
    CONSTRAINT CoursePlan_pk PRIMARY KEY (course_id,semester,exam_id)
);

-- Table Exam
CREATE TABLE Exam (
    exam_id SERIAL  NOT NULL,
    exam_value varchar(20)  NOT NULL,
    CONSTRAINT Exam_pk PRIMARY KEY (exam_id)
);

-- Table Goals
CREATE TABLE Goals (
    instructor_id varchar(10)  NOT NULL,
    student_id varchar(10)  NOT NULL,
    topic_id integer  NOT NULL,
    deadline date  NOT NULL,
    CONSTRAINT Goals_pk PRIMARY KEY (instructor_id,student_id,topic_id)
);

-- Table Instructor
CREATE TABLE Instructor (
    instructor_id varchar(10)  NOT NULL,
    fname varchar(20)  NOT NULL,
    lname varchar(30)  NOT NULL,
    CONSTRAINT Instructor_pk PRIMARY KEY (instructor_id)
);

-- Table Interactions
CREATE TABLE Interactions (
    action_id integer  NOT NULL,
    student_id varchar(10)  NOT NULL,
    start_timestamp timestamp  NOT NULL,
    end_timestamp timestamp  NOT NULL,
    interaction_id SERIAL  NOT NULL,
    topic_id integer  NOT NULL,
    CONSTRAINT Interactions_pk PRIMARY KEY (interaction_id)
);

-- Table Notes
CREATE TABLE Notes (
    student_id varchar(10)  NOT NULL,
    topic_id integer  NOT NULL,
    topic_text text,
    code text,
    CONSTRAINT Notes_pk PRIMARY KEY (student_id,topic_id)
);

-- Table Offering
CREATE TABLE Offering (
    course_id integer  NOT NULL,
    instructor_id varchar(10)  NOT NULL,
    CONSTRAINT Offering_pk PRIMARY KEY (course_id,instructor_id)
);

-- Table Register
CREATE TABLE Register (
    student_id varchar(10)  NOT NULL,
    course_id integer NOT NULL,
    CONSTRAINT Register_pk PRIMARY KEY (student_id,course_id)
);


-- Table Score
CREATE TABLE Score (
    topic_id integer  NOT NULL,
    student_id varchar(10)  NOT NULL,
    score numeric(30,0)  NOT NULL,
    exam_id integer  NOT NULL,
    CONSTRAINT Score_pk PRIMARY KEY (topic_id,student_id)
);

-- Table Student
CREATE TABLE Student (
    student_id varchar(10)  NOT NULL,
    fname varchar(20)  NOT NULL,
    lname varchar(20)  NOT NULL,
    CONSTRAINT Student_pk PRIMARY KEY (student_id)
);

-- Table Topic
CREATE TABLE Topic (
    topic_id SERIAL  NOT NULL,
    topic_name varchar(30)  NOT NULL,
    parent_topic_id integer,
    CONSTRAINT Topic_pk PRIMARY KEY (topic_id)
);

-- Table Users
CREATE TABLE Users (
    username varchar(10)  NOT NULL,
    password varchar(15)  NOT NULL,
    email varchar(25)  NOT NULL,
    role VARCHAR(20)  NOT NULL,
    CONSTRAINT Users_pk PRIMARY KEY (username)
);





-- foreign keys
-- Reference:  Content_Course (table: Content)

ALTER TABLE Content ADD CONSTRAINT Content_Course FOREIGN KEY  (course_id)
    REFERENCES Course;
-- Reference:  Content_Topic (table: Content)

ALTER TABLE Content ADD CONSTRAINT Content_Topic FOREIGN KEY (topic_id)
    REFERENCES Topic;
-- Reference:  CoursePlan_Course (table: CoursePlan)

ALTER TABLE CoursePlan ADD CONSTRAINT CoursePlan_Course FOREIGN KEY  (course_id)
    REFERENCES Course;
-- Reference:  CoursePlan_Exam (table: CoursePlan)

ALTER TABLE CoursePlan ADD CONSTRAINT CoursePlan_Exam FOREIGN KEY  (exam_id)
    REFERENCES Exam ;
-- Reference:  Goals_Instructor (table: Goals)
--###
ALTER TABLE Goals ADD CONSTRAINT Goals_Instructor FOREIGN KEY (instructor_id)
    REFERENCES Instructor;
-- Reference:  Goals_Student (table: Goals)

ALTER TABLE Goals ADD CONSTRAINT Goals_Student FOREIGN KEY (student_id)
    REFERENCES Student;
-- Reference:  Goals_Topic (table: Goals)

ALTER TABLE Goals ADD CONSTRAINT Goals_Topic FOREIGN KEY (topic_id)
    REFERENCES Topic ;
-- Reference:  Instructor_Users (table: Instructor)

ALTER TABLE Instructor ADD CONSTRAINT Instructor_Users FOREIGN KEY (instructor_id)
    REFERENCES Users ;
-- Reference:  Interactions_Actions_tb (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Actions_tb FOREIGN KEY (action_id)
    REFERENCES Actions;
-- Reference:  Interactions_Student (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Student FOREIGN KEY  (student_id)
    REFERENCES Student ;
-- Reference:  Interactions_Topic (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Topic FOREIGN KEY  (topic_id)
    REFERENCES Topic;
-- Reference:  Notes_Student (table: Notes)

ALTER TABLE Notes ADD CONSTRAINT Notes_Student FOREIGN KEY (student_id)
    REFERENCES Student;
-- Reference:  Notes_Topic (table: Notes)

ALTER TABLE Notes ADD CONSTRAINT Notes_Topic FOREIGN KEY  (topic_id)
    REFERENCES Topic ;
-- Reference:  Offering_Course (table: Offering)

ALTER TABLE Offering ADD CONSTRAINT Offering_Course FOREIGN KEY (course_id)
    REFERENCES Course;
-- Reference:  Offering_Instructor (table: Offering)

ALTER TABLE Offering ADD CONSTRAINT Offering_Instructor FOREIGN KEY (instructor_id)
    REFERENCES Instructor;
-- Reference:  Register_Course (table: Register)

ALTER TABLE Register ADD CONSTRAINT Register_Course FOREIGN KEY (course_id)
    REFERENCES Course;
-- Reference:  Register_Student (table: Register)

ALTER TABLE Register ADD CONSTRAINT Register_Student FOREIGN KEY (student_id)
    REFERENCES Student;
-- Reference:  Score_Exam (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Exam FOREIGN KEY (exam_id)
    REFERENCES Exam ;
-- Reference:  Score_Student (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Student FOREIGN KEY (student_id)
    REFERENCES Student;
-- Reference:  Score_Topic (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Topic FOREIGN KEY (topic_id)
    REFERENCES Topic;
-- Reference:  Student_Users (table: Student)

ALTER TABLE Student ADD CONSTRAINT Student_Users FOREIGN KEY (student_id)
    REFERENCES Users;
-- Reference:  Users_Roles (table: Users)

--Alter table to edit/add columns
ALTER TABLE student
ADD COLUMN COURSES_REG integer DEFAULT 0;
  
ALTER TABLE student
ADD COLUMN COURSES_COMPLETE integer DEFAULT 0;
  
ALTER TABLE student
ADD COLUMN TOPICS_COMPLETE integer DEFAULT 0;
  
ALTER TABLE student
ADD COLUMN TOPICS_INCOMPLETE integer DEFAULT 0;

ALTER TABLE student
ADD COLUMN TOPICS_UNTOUCHED integer DEFAULT 0;

ALTER TABLE student
ADD COLUMN CODE_SNIPPETS integer DEFAULT 0;

ALTER TABLE student
ADD COLUMN COMPILATION_FAILS integer DEFAULT 0;

ALTER TABLE student
ADD COLUMN TOPIC_LAST_COMPLETED varchar(30);

ALTER TABLE student
ADD COLUMN DAYS_SINCE_LAST_COMPLETE integer DEFAULT 0;

ALTER TABLE student
ADD COLUMN CURRENT_TOPIC varchar(30);









  
  
  
  
  
  
  
  
  



-- End of file.

