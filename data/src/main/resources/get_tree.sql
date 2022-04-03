with recursive departments_tree
                   (id, name, level, parent_id)
                   as (
        select id, name, 0, parent_id
        from departments
        where departments.parent_id is NULL

        union all
        select mn.id, mn.name, mt.level + 1, mt.id
        from departments mn, departments_tree mt
        where mn.parent_id = mt.id
    )
select * from departments_tree
where level > 0
order by level, parent_id;

with recursive department_tree
                   (id, name, path, level, parent_id)
                   as (
        select id, name, '', 0, parent_id
        from departments
        where parent_id is NULL
        union all
        select
            mn.id,
            mn.name,
            mt.path || '/' || mn.name,
            mt.level + 1,
            mt.id
        from departments mn, department_tree mt
        where mn.parent_id = mt.id
    )
select * from department_tree where level > 0 order by level, parent_id