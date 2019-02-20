alter table transcriptions drop PRIMARY KEY;
alter table transcriptions add column id MEDIUMINT NOT NULL AUTO_INCREMENT, ADD PRIMARY KEY (id);
alter table transcriptions add unique key(`date`,`userid`,`manuscript`,`page`,`line`);
