# flink技术点

## Flink UDF规范

### Flink UDF种类

* Scalar functions map scalar values to a new scalar value.
* Table functions map scalar values to new rows.
* Aggregate functions map scalar values of multiple rows to a new scalar value.
* Table aggregate functions map scalar values of multiple rows to new rows.
* Async table functions are special functions for table sources that perform a lookup.

## Flink sideOutput（侧流输出）

