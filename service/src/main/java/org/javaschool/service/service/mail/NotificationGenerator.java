package org.javaschool.service.service.mail;

import org.javaschool.data.model.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationGenerator {
    @Autowired
    private SendEmail sendEmail;

    public void createEmployeeNotification(Department department) {
        String subject = "Создан новый сотрудник";
        String text = "В " + "[" + department.getDepartmentType().getDepartmentType() + "]" + " с [id = " +
                department.getId() + ", name = " + department.getDepartmentName() + "]" + " был добавлен новый сотрудник";
        sendEmail.sendMail(subject, text);
    }

    public void updateEmployeeNotification(Long employeeId) {
        String subject = "Сотрудник изменен";
        String text = "Сотрудник с [id = " + employeeId + "]" + " был изменен";
        sendEmail.sendMail(subject, text);
    }

    public void deleteEmployeeNotification(Long employeeId) {
        String subject = "Сотрудник удален";
        String text = "Сотрудник с [id = " + employeeId + "]" + " был удален";
        sendEmail.sendMail(subject, text);
    }

    public void createDepartmentNotification(String type, String name) {
        String subject = "Создан новый департамент";
        String text = "Создан новый " + "[" + type + "]" + " с [name = " + name + "]";
        sendEmail.sendMail(subject, text);
    }

    public void updateDepartmentNotification(Long departmentId) {
        String subject = "Департамент изменен";
        String text = "Департамент с [id = " + departmentId + "]" + " был изменен";
        sendEmail.sendMail(subject, text);
    }

    public void deleteDepartmentNotification(Long departmentId) {
        String subject = "Департамент удален";
        String text = "Департамент с [id = " + departmentId + "]" + " был удален";
        sendEmail.sendMail(subject, text);
    }
}
