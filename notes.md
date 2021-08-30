# create database
```
createdb lags
psql lags -f pg_dump.sql
psql lags -f role.sql
psql lags -f grant.sql
```

