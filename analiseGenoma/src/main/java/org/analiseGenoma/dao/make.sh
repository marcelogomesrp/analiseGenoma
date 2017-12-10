#!/bin/bash
model="$1";
modelDao="$1Dao";
file="$1Dao.java";

echo "package org.analiseGenoma.dao;" > $file

echo "import org.analiseGenoma.model.$model;" >> $file

echo "public class $modelDao extends DAO<$model> {" >> $file

echo "	public $modelDao() {">> $file
echo "        super($model.class);">> $file
echo "    }">> $file



echo "    public List<$model> findByName(String name) {">> $file
echo "        List<$model> list = null;">> $file
echo "        try {">> $file
echo "            Query query = manager.createQuery(\"SELECT i FROM $model i WHERE i.name like :name\");">> $file
echo "            query.setParameter(\"name\", name);">> $file
echo "            list = query.getResultList();">> $file
echo "        } catch (NoResultException ex) {">> $file
echo "            System.out.println(\"Erro:: \" + ex.getMessage());">> $file
echo "        }">> $file
echo "        return list;">> $file
echo "    }">> $file


echo "}">> $file
