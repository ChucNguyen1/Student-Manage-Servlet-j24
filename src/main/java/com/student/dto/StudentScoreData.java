package com.student.dto;

import java.util.List;

public class StudentScoreData {
    private StudentInfo student;
    private List<SubjectScore> scores;
    private AttendanceInfo attendance;

    public static class StudentInfo {
        private String name;
        private String className;
        private String semester;

        public StudentInfo() {}

        public StudentInfo(String name, String className, String semester) {
            this.name = name;
            this.className = className;
            this.semester = semester;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }
    }

    public static class SubjectScore {
        private String subject;
        private String type; // "Tự nhiên" hoặc "Xã hội"
        private ScoreDetails scores;
        private Double classAverage;
        private Double previousSemester;
        private String trend; // "up", "down", "stable"

        public SubjectScore() {}

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ScoreDetails getScores() {
            return scores;
        }

        public void setScores(ScoreDetails scores) {
            this.scores = scores;
        }

        public Double getClassAverage() {
            return classAverage;
        }

        public void setClassAverage(Double classAverage) {
            this.classAverage = classAverage;
        }

        public Double getPreviousSemester() {
            return previousSemester;
        }

        public void setPreviousSemester(Double previousSemester) {
            this.previousSemester = previousSemester;
        }

        public String getTrend() {
            return trend;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }
    }

    public static class ScoreDetails {
        private List<Double> oral;
        private List<Double> min15;
        private List<Double> period1;
        private Double midterm;
        private Double finalExam;
        private Double average;

        public ScoreDetails() {}

        public List<Double> getOral() {
            return oral;
        }

        public void setOral(List<Double> oral) {
            this.oral = oral;
        }

        public List<Double> getMin15() {
            return min15;
        }

        public void setMin15(List<Double> min15) {
            this.min15 = min15;
        }

        public List<Double> getPeriod1() {
            return period1;
        }

        public void setPeriod1(List<Double> period1) {
            this.period1 = period1;
        }

        public Double getMidterm() {
            return midterm;
        }

        public void setMidterm(Double midterm) {
            this.midterm = midterm;
        }

        public Double getFinalExam() {
            return finalExam;
        }

        public void setFinalExam(Double finalExam) {
            this.finalExam = finalExam;
        }

        public Double getAverage() {
            return average;
        }

        public void setAverage(Double average) {
            this.average = average;
        }
    }

    public static class AttendanceInfo {
        private int totalDays;
        private int absentDays;
        private int lateDays;

        public AttendanceInfo() {}

        public AttendanceInfo(int totalDays, int absentDays, int lateDays) {
            this.totalDays = totalDays;
            this.absentDays = absentDays;
            this.lateDays = lateDays;
        }

        public int getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(int totalDays) {
            this.totalDays = totalDays;
        }

        public int getAbsentDays() {
            return absentDays;
        }

        public void setAbsentDays(int absentDays) {
            this.absentDays = absentDays;
        }

        public int getLateDays() {
            return lateDays;
        }

        public void setLateDays(int lateDays) {
            this.lateDays = lateDays;
        }
    }

    public StudentScoreData() {}

    public StudentInfo getStudent() {
        return student;
    }

    public void setStudent(StudentInfo student) {
        this.student = student;
    }

    public List<SubjectScore> getScores() {
        return scores;
    }

    public void setScores(List<SubjectScore> scores) {
        this.scores = scores;
    }

    public AttendanceInfo getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceInfo attendance) {
        this.attendance = attendance;
    }
}
