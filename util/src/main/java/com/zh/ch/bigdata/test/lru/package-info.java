package com.zh.ch.bigdata.test.lru;
/*
 *
 * 代码实现
 * 大致思路:
 * 构建双向链表节点ListNode，应包含key,value,prev,next这几个基本属性
 * 对于Cache对象来说，我们需要规定缓存的容量，所以在初始化时，设置容量大小，然后实例化双向链表的head,tail，并让head.next->tail tail.prev->head，这样我们的双向链表构建完成
 * 对于get操作,我们首先查阅hashmap，如果存在的话，直接将Node从当前位置移除，然后插入到链表的首部，在链表中实现删除直接让node的前驱节点指向后继节点，很方便.如果不存在，那么直接返回Null
 * 对于put操作，比较麻烦。
 * @author xzc
 * @description LRU算法实现
 * @date 2021/02/18
 */