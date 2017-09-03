#!/bin/sh
echo -e "\033[31m ===== Stoping The Cluster ====== \033[0m"
#echo -e "\033[31m Stoping Spark Now !!! \033[0m"
#/usr/local/spark/spark-2.1.1-bin-hadoop2.7/sbin/stop-all.sh

# stop hbase cluster
echo -e "\033[31m Stopping hbase cluster Now !!! \033[0m"
/usr/local/hbase/hbase-1.2.4/bin/stop-hbase.sh
echo -e "\n"
sleep 2s

# stop zookeeper on master
echo -e "\033[31m Stopping zookeeper on master Now !!! \033[0m"
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh stop
echo -e "\n"
sleep 2s

# stop zookeeper on node1
echo -e "\033[31m Stopping zookeeper on node1 Now !!! \033[0m"
ssh root@node1 << remotessh
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh stop
echo -e "\n"
sleep 2s
exit
remotessh


# stop zookeeper on node2
echo -e "\033[31m Stopping zookeeper on node2 Now !!! \033[0m"
ssh root@node2 << remotessh
/usr/local/zookeeper/zookeeper-3.4.9/bin/zkServer.sh stop
echo -e "\n"
sleep 2s
exit
remotessh

# stop hadoop cluster
echo -e "\033[31m Stopping Hadoop Now !!! \033[0m"
/usr/local/hadoop/hadoop-2.7.3/sbin/stop-dfs.sh
/usr/local/hadoop/hadoop-2.7.3/sbin/stop-yarn.sh
echo -e "\n"
sleep 5s

#echo -e "\033[31m Stopting Hadoop Now !!! \033[0m"
#/usr/local/hadoop/hadoop-2.7.3/sbin/stop-all.sh
echo -e "\033[31m The Result Of The Command \"jps\" :  \033[0m"
jps
echo -e "\033[31m ======END======== \033[0m"
