/*组合两个表
思路是左连接
P175*/
select FirstName, LastName, City, State from Person p left join Address a
on p.PersonId = a.PersonId;

/*第二高的薪水
P176*/
--思路1：使用聚集函数
select max(Salary) SecondHighestSalary from Employee
where Salary < (select max(Salary) from Employee);

--思路2：使用 limit，使用临时表是为了在不存在第二高时返回 null
select(
    select distinct Salary from Employee
    order by Salary desc limit 1, 1
) as SecondHighestSalary;

/*第 N 高的薪水
思路是使用 limit
P177*/
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N := N - 1;
  RETURN (
    select(
        select distinct Salary from Employee
        order by Salary desc limit N, 1
    ) as SecondHighestSalary
  );
END

/*分数排名
P178*/
select Score,
    (select count(distinct b.Score) from Scores b where b.Score >= a.Score) as Rank
from Scores a
order by a.Score DESC

/*连续出现的数字
思路是笛卡尔积
P180*/
SELECT DISTINCT
    l1.Num AS ConsecutiveNums
FROM
    Logs l1, Logs l2, Logs l3
WHERE
    l1.Id = l2.Id - 1
    AND l2.Id = l3.Id - 1
    AND l1.Num = l2.Num
    AND l2.Num = l3.Num;

/*超过经理收入的员工
P181*/
--方法1：使用嵌套查询
select Name Employee from Employee e1
where e1.Salary > (select Salary from Employee e2
                where e1.ManagerId = e2.Id);

--方法2：使用连接查询
select e1.Name Employee from Employee e1, Employee e2
where e1.ManagerId = e2.Id and e1.Salary > e2.Salary;

/*查找重复的电子邮箱
P82*/
--思路1：嵌套查询计算出现次数
select distinct Email from Person p1
where (select count(*) from Person p2 where p1.Email = p2.Email) > 1;

--思路2：使用 group by 和 having。更推荐这种方法
select Email from Person
group by Email
having count(Email) > 1;

/*从不订购的客户
P183*/
select Name Customers from Customers
where Id not in (select distinct CustomerId from Orders);

/*各部门工资最高的员工
思路是嵌套查询和 group by 限制聚集函数
可以拓展为每个科目分数最高的学生
P184*/
select d.Name Department, e.Name Employee, Salary from Employee e, Department d
where d.Id = e.DepartmentId
    and
    (e.Salary, e.DepartmentId) in (select max(Salary), DepartmentId from Employee group by DepartmentId);

/*查询工资前三高的所有员工
思路是高于他的工资不超过三个*/
select e1.Name Employee, e1.Salary from Employee e1
where(
        select count(distinct e2.Salary)
        from Employee e2
        where e2.Salary > e1.Salary
) < 3;

/*查询各部门工资前三高的所有员工
P185*/
select d.Name Department, e1.Name Employee, Salary
from Department d, Employee e1
where (
        select count(distinct e2.Salary)
        from Employee e2
        where e2.Salary > e1.Salary and e2.DepartmentId = e1.DepartmentId
) < 3 and e1.DepartmentId = d.Id;
