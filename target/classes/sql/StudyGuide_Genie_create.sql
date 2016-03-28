
-- tables
-- Table Actions
CREATE TABLE Actions (
    action_value varchar(20)  NOT NULL,
    action_id numeric(30,0)  NOT NULL,
    CONSTRAINT Actions_pk PRIMARY KEY (action_id)
);

-- Table Content
CREATE TABLE Content (
    course_id numeric(30,0)  NOT NULL,
    topic_id numeric(30,0)  NOT NULL,
    CONSTRAINT Content_pk PRIMARY KEY (course_id,topic_id)
);

-- Table Course
CREATE TABLE Course (
    course_level numeric(3,0)  NOT NULL,
    course_id numeric(30,0)  NOT NULL,
    course_title varchar(50)  NOT NULL,
    CONSTRAINT Course_pk PRIMARY KEY (course_id)
);

-- Table CoursePlan
CREATE TABLE CoursePlan (
    course_id numeric(30,0)  NOT NULL,
    semester varchar(10)  NOT NULL,
    exam_id numeric(15,0)  NOT NULL,
    start_date date  NOT NULL,
    CONSTRAINT CoursePlan_pk PRIMARY KEY (course_id,semester,exam_id)
);

-- Table Exam
CREATE TABLE Exam (
    exam_id numeric(15,0)  NOT NULL,
    exam_value varchar(20)  NOT NULL,
    CONSTRAINT Exam_pk PRIMARY KEY (exam_id)
);

-- Table Goals
CREATE TABLE Goals (
    instructor_id numeric(30,0)  NOT NULL,
    student_id numeric(40,0)  NOT NULL,
    topic_id numeric(30,0)  NOT NULL,
    deadline date  NOT NULL,
    CONSTRAINT Goals_pk PRIMARY KEY (instructor_id,student_id,topic_id)
);

-- Table Instructor
CREATE TABLE Instructor (
    instructor_id numeric(30,0)  NOT NULL,
    fname varchar(20)  NOT NULL,
    lname varchar(30)  NOT NULL,
    CONSTRAINT Instructor_pk PRIMARY KEY (instructor_id)
);

-- Table Interactions
CREATE TABLE Interactions (
    action_id numeric(30,0)  NOT NULL,
    student_id numeric(30,0)  NOT NULL,
    start_timestamp timestamp  NOT NULL,
    end_timestamp timestamp  NOT NULL,
    interaction_id numeric(30,0)  NOT NULL,
    topic_id numeric(30,0)  NOT NULL,
    CONSTRAINT Interactions_pk PRIMARY KEY (interaction_id)
);

-- Table Notes
CREATE TABLE Notes (
    student_id numeric(40,0)  NOT NULL,
    topic_id numeric(30,0)  NOT NULL,
    text text  NOT NULL,
    code text  NOT NULL,
    CONSTRAINT Notes_pk PRIMARY KEY (student_id,topic_id)
);

-- Table Offering
CREATE TABLE Offering (
    course_id numeric(30,0)  NOT NULL,
    instructor_id numeric(30,0)  NOT NULL,
    CONSTRAINT Offering_pk PRIMARY KEY (course_id,instructor_id)
);

-- Table Register
CREATE TABLE Register (
    student_id numeric(40,0)  NOT NULL,
    course_id numeric(30,0)  NOT NULL,
    CONSTRAINT Register_pk PRIMARY KEY (student_id,course_id)
);

-- Table Roles
CREATE TABLE Roles (
    role_id numeric(20,0)  NOT NULL,
    role_value varchar(20)  NOT NULL,
    CONSTRAINT Roles_pk PRIMARY KEY (role_id)
);

-- Table Score
CREATE TABLE Score (
    topic_id numeric(30,0)  NOT NULL,
    student_id numeric(40,0)  NOT NULL,
    score numeric(30,0)  NOT NULL,
    exam_id numeric(15,0)  NOT NULL,
    CONSTRAINT Score_pk PRIMARY KEY (topic_id,student_id)
);

-- Table Student
CREATE TABLE Student (
    student_id numeric(40,0)  NOT NULL,
    fname varchar(20)  NOT NULL,
    lname varchar(20)  NOT NULL,
    CONSTRAINT Student_pk PRIMARY KEY (student_id)
);

-- Table Topic
CREATE TABLE Topic (
    topic_id numeric(30,0)  NOT NULL,
    topic_name varchar(30)  NOT NULL,
    parent_topic_id numeric(30,0)  NOT NULL,
    CONSTRAINT Topic_pk PRIMARY KEY (topic_id)
);

-- Table Users
CREATE TABLE Users (
    user_id numeric(40,0)  NOT NULL,
    username varchar(10)  NOT NULL,
    password varchar(15)  NOT NULL,
    email varchar(25)  NOT NULL,
    role_id numeric(20,0)  NOT NULL,
    CONSTRAINT Users_pk PRIMARY KEY (user_id)
);





-- foreign keys
-- Reference:  Content_Course (table: Content)

ALTER TABLE Content ADD CONSTRAINT Content_Course FOREIGN KEY Content_Course (course_id)
    REFERENCES Course (course_id);
-- Reference:  Content_Topic (table: Content)

ALTER TABLE Content ADD CONSTRAINT Content_Topic FOREIGN KEY Content_Topic (topic_id)
    REFERENCES Topic (topic_id);
-- Reference:  CoursePlan_Course (table: CoursePlan)

ALTER TABLE CoursePlan ADD CONSTRAINT CoursePlan_Course FOREIGN KEY CoursePlan_Course (course_id)
    REFERENCES Course (course_id);
-- Reference:  CoursePlan_Exam (table: CoursePlan)

ALTER TABLE CoursePlan ADD CONSTRAINT CoursePlan_Exam FOREIGN KEY CoursePlan_Exam (exam_id)
    REFERENCES Exam (exam_id);
-- Reference:  Goals_Instructor (table: Goals)

ALTER TABLE Goals ADD CONSTRAINT Goals_Instructor FOREIGN KEY Goals_Instructor (instructor_id)
    REFERENCES Instructor (instructor_id);
-- Reference:  Goals_Student (table: Goals)

ALTER TABLE Goals ADD CONSTRAINT Goals_Student FOREIGN KEY Goals_Student (student_id)
    REFERENCES Student (student_id);
-- Reference:  Goals_Topic (table: Goals)

ALTER TABLE Goals ADD CONSTRAINT Goals_Topic FOREIGN KEY Goals_Topic (topic_id)
    REFERENCES Topic (topic_id);
-- Reference:  Instructor_Users (table: Instructor)

ALTER TABLE Instructor ADD CONSTRAINT Instructor_Users FOREIGN KEY Instructor_Users (instructor_id)
    REFERENCES Users (user_id);
-- Reference:  Interactions_Actions_tb (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Actions_tb FOREIGN KEY Interactions_Actions_tb (action_id)
    REFERENCES Actions (action_id);
-- Reference:  Interactions_Student (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Student FOREIGN KEY Interactions_Student (student_id)
    REFERENCES Student (student_id);
-- Reference:  Interactions_Topic (table: Interactions)

ALTER TABLE Interactions ADD CONSTRAINT Interactions_Topic FOREIGN KEY Interactions_Topic (topic_id)
    REFERENCES Topic (topic_id);
-- Reference:  Notes_Student (table: Notes)

ALTER TABLE Notes ADD CONSTRAINT Notes_Student FOREIGN KEY Notes_Student (student_id)
    REFERENCES Student (student_id);
-- Reference:  Notes_Topic (table: Notes)

ALTER TABLE Notes ADD CONSTRAINT Notes_Topic FOREIGN KEY Notes_Topic (topic_id)
    REFERENCES Topic (topic_id);
-- Reference:  Offering_Course (table: Offering)

ALTER TABLE Offering ADD CONSTRAINT Offering_Course FOREIGN KEY Offering_Course (course_id)
    REFERENCES Course (course_id);
-- Reference:  Offering_Instructor (table: Offering)

ALTER TABLE Offering ADD CONSTRAINT Offering_Instructor FOREIGN KEY Offering_Instructor (instructor_id)
    REFERENCES Instructor (instructor_id);
-- Reference:  Register_Course (table: Register)

ALTER TABLE Register ADD CONSTRAINT Register_Course FOREIGN KEY Register_Course (course_id)
    REFERENCES Course (course_id);
-- Reference:  Register_Student (table: Register)

ALTER TABLE Register ADD CONSTRAINT Register_Student FOREIGN KEY Register_Student (student_id)
    REFERENCES Student (student_id);
-- Reference:  Score_Exam (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Exam FOREIGN KEY Score_Exam (exam_id)
    REFERENCES Exam (exam_id);
-- Reference:  Score_Student (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Student FOREIGN KEY Score_Student (student_id)
    REFERENCES Student (student_id);
-- Reference:  Score_Topic (table: Score)

ALTER TABLE Score ADD CONSTRAINT Score_Topic FOREIGN KEY Score_Topic (topic_id)
    REFERENCES Topic (topic_id);
-- Reference:  Student_Users (table: Student)

ALTER TABLE Student ADD CONSTRAINT Student_Users FOREIGN KEY Student_Users (student_id)
    REFERENCES Users (user_id);
-- Reference:  Users_Roles (table: Users)

ALTER TABLE Users ADD CONSTRAINT Users_Roles FOREIGN KEY Users_Roles (role_id)
    REFERENCES Roles (role_id);



-- End of file.

