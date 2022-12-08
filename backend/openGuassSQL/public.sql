/*
 Navicat Premium Data Transfer

 Source Server         : 华为云数据库
 Source Server Type    : PostgreSQL
 Source Server Version : 90204 (90204)
 Source Host           : 120.46.149.89:26000
 Source Catalog        : zhw_DB
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90204 (90204)
 File Encoding         : 65001

 Date: 29/11/2022 21:44:49
*/


-- ----------------------------
-- Sequence structure for class_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."class_id_seq";
CREATE SEQUENCE "public"."class_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for classroom_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."classroom_id_seq";
CREATE SEQUENCE "public"."classroom_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for course_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."course_id_seq";
CREATE SEQUENCE "public"."course_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for enroll_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."enroll_id_seq";
CREATE SEQUENCE "public"."enroll_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for institute_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."institute_id_seq";
CREATE SEQUENCE "public"."institute_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for student_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."student_id_seq";
CREATE SEQUENCE "public"."student_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for table_name_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."table_name_id_seq";
CREATE SEQUENCE "public"."table_name_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_id_seq";
CREATE SEQUENCE "public"."user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS "public"."class";
CREATE TABLE "public"."class" (
  "id" int4 NOT NULL DEFAULT nextval('class_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "grade" int4,
  "institute_id" int4
)
;
COMMENT ON TABLE "public"."class" IS '班级表';

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO "public"."class" VALUES (3, '计算机类1班', 2020, 2);
INSERT INTO "public"."class" VALUES (1, '自动化1班', 2020, 1);
INSERT INTO "public"."class" VALUES (4, '计算机类2班', 2020, 2);
INSERT INTO "public"."class" VALUES (2, '自动化1班', 2021, 1);

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS "public"."classroom";
CREATE TABLE "public"."classroom" (
  "id" int4 NOT NULL DEFAULT nextval('classroom_id_seq'::regclass),
  "building" varchar(40) COLLATE "pg_catalog"."default",
  "name" varchar(20) COLLATE "pg_catalog"."default",
  "type" varchar(20) COLLATE "pg_catalog"."default",
  "capacity" int4
)
;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO "public"."classroom" VALUES (1, '西一楼', '101', '多媒体教室', 120);
INSERT INTO "public"."classroom" VALUES (2, '西一楼', '102', '多媒体教室', 120);
INSERT INTO "public"."classroom" VALUES (3, '西一楼', '103', '多媒体教室', 120);
INSERT INTO "public"."classroom" VALUES (4, '西二楼', '101', '普通教室', 100);
INSERT INTO "public"."classroom" VALUES (5, '西二楼', '102', '普通教室', 100);
INSERT INTO "public"."classroom" VALUES (6, '西二楼', '103', '普通教室', 100);
INSERT INTO "public"."classroom" VALUES (7, '东三楼', '101', '梯形教室', 120);
INSERT INTO "public"."classroom" VALUES (8, '东三楼', '102', '梯形教室', 120);
INSERT INTO "public"."classroom" VALUES (9, '东三楼', '103', '多媒体教室', 100);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS "public"."course";
CREATE TABLE "public"."course" (
  "id" int4 NOT NULL DEFAULT nextval('course_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "credit" int4,
  "course_hour" int4,
  "year" int4,
  "semester" varchar(20) COLLATE "pg_catalog"."default",
  "institute_id" int4,
  "teacher_id" int4,
  "classroom_id" int4,
  "limit_number" int4,
  "what_day" varchar(20) COLLATE "pg_catalog"."default",
  "day_time" varchar(20) COLLATE "pg_catalog"."default",
  "status" int4 DEFAULT 1
)
;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO "public"."course" VALUES (3, '模拟电路', 3, 56, 2022, '上学期', 1, 1, 1, 80, '星期一', '3~4节', 1);
INSERT INTO "public"."course" VALUES (4, '模拟电路', 3, 56, 2022, '上学期', 1, 1, 1, 80, '星期一', '1~2节', 1);
INSERT INTO "public"."course" VALUES (5, '模拟电路', 3, 56, 2022, '上学期', 1, 1, 1, 80, '星期一', '5~6节', 1);
INSERT INTO "public"."course" VALUES (6, '模拟电路', 3, 56, 2022, '上学期', 1, 1, 1, 80, '星期一', '7~8节', 1);
INSERT INTO "public"."course" VALUES (7, '模拟电路', 3, 56, 2022, '上学期', 1, 1, 1, 80, '星期一', '9~11节', 1);
INSERT INTO "public"."course" VALUES (8, '测试修改', 5, 64, 2021, '下学期', 2, 2, 3, 80, '星期五', '1~2节', 0);

-- ----------------------------
-- Table structure for enroll
-- ----------------------------
DROP TABLE IF EXISTS "public"."enroll";
CREATE TABLE "public"."enroll" (
  "id" int4 NOT NULL DEFAULT nextval('enroll_id_seq'::regclass),
  "student_id" int4,
  "course_id" int4
)
;

-- ----------------------------
-- Records of enroll
-- ----------------------------
INSERT INTO "public"."enroll" VALUES (3, 4, 3);

-- ----------------------------
-- Table structure for institute
-- ----------------------------
DROP TABLE IF EXISTS "public"."institute";
CREATE TABLE "public"."institute" (
  "id" int4 NOT NULL DEFAULT nextval('institute_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "address" varchar(200) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."institute"."name" IS '学院名称';
COMMENT ON COLUMN "public"."institute"."address" IS '学院地址';
COMMENT ON TABLE "public"."institute" IS '学院表';

-- ----------------------------
-- Records of institute
-- ----------------------------
INSERT INTO "public"."institute" VALUES (1, '电气工程与自动化学院', '学府路1号');
INSERT INTO "public"."institute" VALUES (2, '计算机与大数据学院', '学府路2号');
INSERT INTO "public"."institute" VALUES (4, '数学与统计学院', '学府路3号');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS "public"."student";
CREATE TABLE "public"."student" (
  "id" int4 NOT NULL DEFAULT nextval('student_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default",
  "sex" varchar(20) COLLATE "pg_catalog"."default",
  "birthday" timestamp(0),
  "province" varchar(50) COLLATE "pg_catalog"."default",
  "city" varchar(100) COLLATE "pg_catalog"."default",
  "class_id" int4,
  "user_id" int4
)
;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO "public"."student" VALUES (1, '张三', '男', '2002-11-26 14:22:38', '福建省', '福州', 3, 2);
INSERT INTO "public"."student" VALUES (3, '李磊', '男', '2001-11-26 08:00:00', '福建省', '莆田', 3, 4);
INSERT INTO "public"."student" VALUES (2, '韩梅梅', '女', '2001-11-27 00:00:00', '福建省', '三明', 2, 3);
INSERT INTO "public"."student" VALUES (4, '赵宁', '女', '2002-11-29 17:35:08', '福建', '泉州', 1, 8);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS "public"."teacher";
CREATE TABLE "public"."teacher" (
  "id" int4 NOT NULL DEFAULT nextval('table_name_id_seq'::regclass),
  "name" varchar(20) COLLATE "pg_catalog"."default",
  "degree" varchar(20) COLLATE "pg_catalog"."default",
  "title" varchar(20) COLLATE "pg_catalog"."default",
  "birthday" timestamp(0),
  "hire_date" timestamp(0),
  "institute_id" int4,
  "user_id" int4,
  "sex" varchar(10) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO "public"."teacher" VALUES (1, '老师1', '博士', '副教授', '1987-09-27 18:46:47', '2022-11-27 13:53:09', 1, 5, '男');
INSERT INTO "public"."teacher" VALUES (2, '老师2', '硕士', '讲师', '1995-11-27 00:00:00', '2022-11-27 18:50:28', 2, 6, '女');
INSERT INTO "public"."teacher" VALUES (3, '老师3', '博士', '教授', '2022-11-29 00:00:00', '2022-11-29 14:35:35', 4, 7, '女');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "id" int4 NOT NULL DEFAULT nextval('user_id_seq'::regclass),
  "username" varchar(100) COLLATE "pg_catalog"."default",
  "password" varchar(100) COLLATE "pg_catalog"."default",
  "photo" varchar(1500) COLLATE "pg_catalog"."default",
  "user_type" varchar(100) COLLATE "pg_catalog"."default",
  "status" int4 DEFAULT 1
)
;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (3, 'S2211261427452', '$2a$10$jTwkI8DiHMyo/JwaGUoef.OFBobYdxG.cVrUIOoI8C1P6HNTPlOyO', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'student', 1);
INSERT INTO "public"."user" VALUES (4, 'S2211261428255', '$2a$10$icaY5a.iexhUEFqXkXhv4.z5XC3mSRbj0/Z8du6oZyeotwtf30Ck2', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'student', 1);
INSERT INTO "public"."user" VALUES (1, 'admin', '$2a$10$w4pVzwNMM852p1RQlETYle2JA.Wv1v2Zl7hVmL4ZGCTke.XVyOQ2G', 'https://cdn.acwing.com/media/user/profile/photo/13557_lg_eb1ced6a92.jpg', 'superAdmin', 1);
INSERT INTO "public"."user" VALUES (5, 'T2211271353098', '$2a$10$bg./p/JFrzGbdq.kps.gGOW7OU/KNtPph9qsK644EDxMZGI8HYE.a', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'teacher', 1);
INSERT INTO "public"."user" VALUES (6, 'T2211271850273', '$2a$10$xG4UmUx7Tkq/AgrRYTUgEuV8JxbE.5YggG5tny2cdA.2wu5xI.pIy', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'teacher', 1);
INSERT INTO "public"."user" VALUES (7, 'T2211291435348', '$2a$10$gQlMvr.7IgT7lWTSlNa5yOTVntoIBbm4OH.TO5i4g3R8to6W88wfy', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'teacher', 1);
INSERT INTO "public"."user" VALUES (2, 'S2211261424174', '$2a$10$zI5Uvu7J7Cr7SoFIeGU2OON3Sto.IMQUH8ewYYg3O2iZl0fiWAf6O', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'student', 1);
INSERT INTO "public"."user" VALUES (8, 'S2211291736386', '$2a$10$.YLma2JQD3I/UwgNP5YmNe1iu8T4VzknesudWsYN8GxpQk/iD/8QO', 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png', 'student', 1);

-- ----------------------------
-- Function structure for score_insert_func
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."score_insert_func"();
CREATE OR REPLACE FUNCTION "public"."score_insert_func"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
declare
begin
insert into score_insert_trigger values(new.sno, new.cno, new.score);
return new;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for tab_insert_func
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."tab_insert_func"();
CREATE OR REPLACE FUNCTION "public"."tab_insert_func"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
declare
begin
insert into test_trigger_tab values(new.id1, new.id2, new.id3);
return new;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for teacher_sum
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."teacher_sum"(OUT "s" int4);
CREATE OR REPLACE FUNCTION "public"."teacher_sum"(OUT "s" int4)
  RETURNS "pg_catalog"."int4" AS $BODY$ DECLARE 
begin
select count(*) into s from teacher;
end

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."class_id_seq"
OWNED BY "public"."class"."id";
SELECT setval('"public"."class_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."classroom_id_seq"
OWNED BY "public"."classroom"."id";
SELECT setval('"public"."classroom_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."course_id_seq"
OWNED BY "public"."course"."id";
SELECT setval('"public"."course_id_seq"', 8, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."enroll_id_seq"
OWNED BY "public"."enroll"."id";
SELECT setval('"public"."enroll_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."institute_id_seq"
OWNED BY "public"."institute"."id";
SELECT setval('"public"."institute_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."student_id_seq"
OWNED BY "public"."student"."id";
SELECT setval('"public"."student_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."table_name_id_seq"
OWNED BY "public"."teacher"."id";
SELECT setval('"public"."table_name_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."user_id_seq"
OWNED BY "public"."user"."id";
SELECT setval('"public"."user_id_seq"', 8, true);

-- ----------------------------
-- Primary Key structure for table class
-- ----------------------------
ALTER TABLE "public"."class" ADD CONSTRAINT "class_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table classroom
-- ----------------------------
ALTER TABLE "public"."classroom" ADD CONSTRAINT "classroom_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table course
-- ----------------------------
ALTER TABLE "public"."course" ADD CONSTRAINT "course_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table enroll
-- ----------------------------
ALTER TABLE "public"."enroll" ADD CONSTRAINT "enroll_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table institute
-- ----------------------------
ALTER TABLE "public"."institute" ADD CONSTRAINT "institute_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "public"."student" ADD CONSTRAINT "student_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table teacher
-- ----------------------------
ALTER TABLE "public"."teacher" ADD CONSTRAINT "table_name_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");
