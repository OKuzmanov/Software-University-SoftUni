SELECT `first_name`, `last_name` FROM `employees`
WHERE `department_id` NOT IN(4)
ORDER BY `employee_id` ASC;