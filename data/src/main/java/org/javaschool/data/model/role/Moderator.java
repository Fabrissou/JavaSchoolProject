package org.javaschool.data.model.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javaschool.data.model.department.Department;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("moderator")
@ToString(callSuper = true)
public class Moderator extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;

}
