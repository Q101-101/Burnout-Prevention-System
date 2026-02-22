package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI permits ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) {

        if (subjects == null || subjects.length == 0) {
            throw new IllegalArgumentException("Semester planner's subjects must not be null or empty");
        }
        if (maximumSlackTime <= 0) {
            throw new IllegalArgumentException("Semester planner's maximumSlackTime must not be negative or 0");
        }
        if (semesterDuration <= 0) {
            throw new IllegalArgumentException("Semester planner's semesterDuration must not be negative or 0");
        }

        int totalStudyTime = 0;
        int totalSlackTime = 0;
        for (UniversitySubject subject : subjects) {
            totalStudyTime += subject.neededStudyTime();
            totalSlackTime += switch (subject.category()) {
                case MATH -> (int) Math.ceil(subject.neededStudyTime() * 0.2);
                case PROGRAMMING -> (int) Math.ceil(subject.neededStudyTime() * 0.1);
                case THEORY -> (int) Math.ceil(subject.neededStudyTime() * 0.15);
                case PRACTICAL -> (int) Math.ceil(subject.neededStudyTime() * 0.05);
            };
        }

        if (totalSlackTime > maximumSlackTime) {
            throw new DisappointmentException();
        }

        int jarCount = totalStudyTime / 5;
        if (semesterDuration < totalSlackTime + totalStudyTime) {
            jarCount *= 2;
        }

        return jarCount;

    }
}
