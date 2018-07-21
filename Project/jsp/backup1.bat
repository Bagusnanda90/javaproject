e:
cd\
cd dimata\daily-dbbackup
del harisma6.sql
ren harisma5.sql harisma6.sql
ren harisma4.sql harisma5.sql
ren harisma3.sql harisma4.sql
ren harisma2.sql harisma3.sql
ren harisma1.sql harisma2.sql
ren harisma.sql harisma1.sql
cd\
cd Program Files\MySQL\MySQL Server 4.1\bin
mysqldump --opt -u root harisma > harisma.sql
mysqldump --opt -u root harisma > E:\dimata\daily-dbbackup\harisma.sql


