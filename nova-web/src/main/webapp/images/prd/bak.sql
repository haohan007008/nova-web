create table tmp_prd_color as 
select a.TABLE_NAME Id,
       a.TABLE_NAME prdID,
       a.TABLE_NAME colorNo,
       a.TABLE_NAME colorName
  from user_tables a
 where 1 = 2;
 
 
 delete from  tmp_prd_color where id =89;
 
 select * from tmp_prd_color WHERE ID = 109
 select count(1) from tmp_ord_item --561
 
 select * from tmp_ord_item 
 
 create table tmp_aaaa as
select b.orderid,c.prdid,a.* from tmp_ord_item a 
        inner join tmp_order_1 b on a.custname = b.custname
        inner join tmp_prd c on a.prdno = c.prdno ;
        
        select * from (
        select a.orderid,c.colorid from tmp_aaaa a
        left join tmp_color_1 c on c.colorname = a.colorname and a.prdid = c.prdid) cc 
        group by orderid,colorid
        having count(1) > 1
        
select * from tmp_aaaa where orderid = 90
select * from tmp_color_1 where color

SELECT 'INSERT INTO r_orderitem(orderId,prdId,prdColorId,prdColorNo,prdColorName,ns,nm,nl,nxl,prdNum,price,subTotal)' ||
       'VALUES(' || ORDERID || ',' || PRDID || ',' || COLORID || ',''' ||
       COLORNO || ''',''' || COLORNAME || ''',' || NVL(NS,0) || ',' ||  NVL(NM,0) || ',' || NVL(NLL,0) || ',' || NVL(NXL,0) || ','|| NVL(PRDCOUNT,0) || ',' ||
       DECODE(PRICE,NULL, BATCHPRICE, PRICE) || ',' || TOTALAMOUNT || ');'
  FROM (select a.*,
               (select colorid
                  from tmp_color_1 c
                 where c.colorname = a.colorname
                   and a.prdid = c.prdid
                   and rownum = 1) colorid
          from tmp_aaaa a)
