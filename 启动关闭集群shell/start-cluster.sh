#!/bin/sh
echo -e "\033[31m ========Start The Cluster======== \033[0m"

# start hadoop cluster
echo -e "\033[31m Starting Hadoop Now !!! \033[0m"
/usr/local/hadoop/hadoop-2.7.3/sbin/start-dfs.sh
/usr/local/hadoop/hadoop-2.7.3/sbin/start-yarn.sh
echo -e "\n"
sleep 2s

# start zookeeper on master
echo -e "\033[31m Starting zookeeper on master Now !!! \033[0m"
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh start
echo -e "\n"
sleep 2s

# start zookeeper on node1
echo -e "\033[31m Starting zookeeper on node1 Now !!! \033[0m"
ssh root@node1 << remotessh
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh start
echo -e "\n"
sleep 2s
exit
remotessh


# start zookeeper on node1
echo -e "\033[31m Starting zookeeper on node2 Now !!! \033[0m"
ssh root@node2 << remotessh
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh start
echo -e "\n"
sleep 2s
exit
remotessh

# start hbase cluster
echo -e "\033[31m Starting hbase cluster Now !!! \033[0m"
/usr/local/hbase/hbase-1.2.4/bin/start-hbase.sh
echo -e "\n"
sleep 2s 


#echo -e "\033[31m Starting Spark Now !!! \033[0m"
#/usr/local/spark/spark-2.1.1-bin-hadoop2.7/sbin/start-all.sh
echo -e "\033[31m The Result Of The Command \"jps\" :  \033[0m"
jps
echo -e "\033[31m ========END======== \033[0m"
