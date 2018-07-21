c:

cd\backupdb
del harisma_canteen_visitation_1st.sql
copy harisma_canteen_visitation_2nd.sql harisma_canteen_visitation_1st.sql
del harisma_canteen_visitation_2nd.sql 
copy harisma_canteen_visitation_3rd.sql harisma_canteen_visitation_2nd.sql
del harisma_canteen_visitation_3rd.sql

cd\mysql\bin
mysqldump -h 192.0.0.10 -u root harismabd hr_canteen_visitation > c:\backupdb\harisma_canteen_visitation_3rd.sql


exit

