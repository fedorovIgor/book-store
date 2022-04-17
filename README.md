## service - library of books

 - search for books by author and genre
 - viewing your books

### achievement points:
- paginated response is implemented
- implemented authorization and authentication on sessions
- sessions are stored in Redis
- configured docker compos for Postgres, Prometheus, Grafana, Redis.

### main models:
<p align="center">
  <img src="img/BookStore Model UML.jpg" width="350" title="hover text">
</p>

### changes: 
#### user
- `RoleEntity.roleName` should be like this - `ROLE_ADMIN`
- `RoleEntity.authorities` should be like - `CREATE_BOOK` or `CHANGE_ROLE`
- the `RoleEntity.roleName` and
`AuthoritiesEntity.name` have the same behavior - `authorities` for SpringSecurity.