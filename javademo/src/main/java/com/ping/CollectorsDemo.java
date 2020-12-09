package com.ping;

import com.sun.javafx.event.EventDispatchTreeImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author pingjianchao
 * @Date 2020/12/8
 */
public class CollectorsDemo {
    public static void main(String[] args) {
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 6);
        //CollectersToListDemo(integers);
        //CollectorsToSetDemo(integers);
        //CollectorsToCollectionDemo(integers);
        //CollectorsToCountingDemo(integers);
        //CollectorsMinByDemo(integers);
        //CollectorsMaxByDemo(integers);
        List<String> strings = Arrays.asList("a", "alpha", "beta", "gama","beta");
        //CollectorsPartitioningByDemo(strings);
        //CollectorsJoiningDemo(strings);
        //final List<Long> longs = Arrays.asList(100L, 200L, 300L);
        //CollectorsAveragingLongDemo(longs);
        //CollectorsAveragingIntDemo(integers);
        //CollectorsToMapDemo(strings);
        //collectorsToMapDeduplicationDemo(strings);
        collectorsGroupingByDemo(strings);
    }

    /**
     * 分组函数：groupingBy()
     * groupingBy()是一种高级方法，用于从任何其他集合创建Map.
     *
     * @param strings
     */
    private static void collectorsGroupingByDemo(List<String> strings) {
        final Map<Integer, List<String>> collect = strings.stream().collect(Collectors.groupingBy(String::length));
        final Map<Integer, LinkedList<String>> collect1 = strings.stream().collect(Collectors.groupingBy(String::length,
                /*这里指定了Map中需要的列表类型（LinkedList）*/
                Collectors.toCollection(LinkedList::new)));
        for (Map.Entry<Integer,List<String>> entry : collect.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    /**
     * 在创建Map时处理列表的重复项
     * 集合中可以包含重复的值，因此，如果想从列表中创建一个Map，并希望集合中的key作为map的key,
     * 那么需要解析重复的key.由于map只包含唯一的key,可以使用比较器来实现这一点。
     * Funtion.identity()指向集合中的值，i1和i2是重复键的值。可以只保留一个值，这里选择i1，
     * 也可以用这两个值来计算任何东西，比如把它们相加，比较和选择较大的那个，等等
     * @param strings
     */
    private static void collectorsToMapDeduplicationDemo(List<String> strings) {
        final Map<String, Integer> s = strings.stream().collect(Collectors.toMap(Function.identity(), String::length, (i1, i2) -> i1));
        for (Map.Entry<String,Integer> entry : s.entrySet()){
            final String key = entry.getKey();
            System.out.println("key:"+key);
            final Integer integer = s.get(key);
            System.out.println("value:"+integer);
        }
    }

    /**
     * 创建Map:toMap()
     * 根据集合的值创建Map
     * 创建了一个Map,其中集合值作为key，集合值的长度作为value
     * @param strings
     */
    private static void collectorsToMapDemo(List<String> strings) {
        final Map<String, Integer> collect = strings.stream().collect(Collectors.toMap(Function.identity(), String::length));
        for (Map.Entry<String,Integer> entry : collect.entrySet()){
            final String key = entry.getKey();
            System.out.println("key:"+key);
            final Integer integer = collect.get(key);
            System.out.println("value:"+integer);
        }
    }

    /**
     * Integer 类型集合的平均值：averagingInt()
     * 查找Integer类型集合的平均值
     * 注意： 返回的是Double类型而不是int类型
     * @param integers
     */
    private static void collectorsAveragingIntDemo(List<Integer> integers) {
        final Double collect = integers.stream().collect(Collectors.averagingInt(a -> a * 2));
        System.out.println("averageingInt:"+collect);
    }

    /**
     * 查找long类型的平均值：averagingLong()
     * 注意：返回的是Double类型而不是Long类型
     * @param longs
     */
    private static void collectorsAveragingLongDemo(List<Long> longs) {
        final Double collect = longs.stream().collect(Collectors.averagingLong(x -> x * 2));
        System.out.println("averagingLong:"+collect);
    }

    /**
     * 连接元素：joining
     * 用指定的字符串链接集合内的元素
     * @param strings
     */
    private static void collectorsJoiningDemo(List<String> strings) {
        final String collect = strings.stream().distinct().collect(Collectors.joining(","));
        System.out.println("joining:"+collect);
        final String collect1 = strings.stream().map(a -> a.toString()).collect(Collectors.joining(",", "[", "]"));
        System.out.println("joining:"+collect1);
    }

    /**
     * 分区列表：partitioningBy()
     * 用于将一个集合划分为2个集合并将其添加到映射中，一个满足条件，另一个不满足，例如
     * 从集合中分离奇偶数。因此它将在map中生成两条数据，一个以true为key，奇数为值，
     * 第二个以false为key，以偶数为值。
     * 这里我们将长度大于2的字符串与其余字符串分开
     * @param strings
     */
    private static void collectorsPartitioningByDemo(List<String> strings) {
        final Map<Boolean, List<String>> collect = strings.stream().collect(Collectors.partitioningBy(a -> a.length() > 2));
        for (Map.Entry<Boolean,List<String>> entry: collect.entrySet()){
            final Boolean key = entry.getKey();
            System.out.println("key:"+key+collect.get(key));
        }
    }

    /**
     * 和最小值类似，使用maxBy()方法来获得最大值
     * @param integers
     */
    private static void collectorsMaxByDemo(List<Integer> integers) {
        final Integer integer = integers.stream().collect(Collectors.maxBy(Comparator.naturalOrder())).get();
        System.out.println("maxBy integer : "+integer);
    }

    /**
     * 求最小值：minBy()
     * 用于返回列表中存在最小值 按照整数排序返回1，按照字符串排序返回C++
     * 可以使用reverseOrder()方法反转反序
     * 同时可以自定义对象比较器
     * @param integers
     */
    private static void collectorsMinByDemo(List<Integer> integers) {
        final List<String> list = Arrays.asList("java", "c++", "python");
        final Integer integer = integers.stream()
                .collect(Collectors.minBy(Comparator.naturalOrder())).get();
        final String s = list.stream().collect(Collectors.minBy(Comparator.naturalOrder())).get();
        System.out.println("minBy integer:"+integer);
        System.out.println("minBy String:"+s);
        System.out.println("-----------------------------");
        final Integer reverseOrder = integers.stream().collect(Collectors.minBy(Comparator.reverseOrder())).get();
        System.out.println("minBy integer reverseOrder:"+reverseOrder);
        final String s1 = list.stream().collect(Collectors.minBy(Comparator.reverseOrder())).get();
        System.out.println("minBy String reverseOrder:"+s1);

    }

    /**
     * 计算元素数量： counting()
     * 用于返回计算集合中存在的元素个数
     * @param integers
     */
    private static void collectorsToCountingDemo(List<Integer> integers) {
        final Long count = integers.stream().filter(a -> a < 5)
                .collect(Collectors.counting());
        System.out.println("counting:"+count);
    }

    /**
     * 返回指定的集合：toCollection()
     * 可以将元素集合累积到指定的集合中
     * @param integers
     */
    private static void collectorsToCollectionDemo(List<Integer> integers) {
        integers.stream().map(a->a*2)
                .collect(Collectors.toCollection(LinkedList::new)).forEach(a->{
            System.out.println("LinkedList:"+a);
        });
        integers.forEach(a->{
            System.out.println("integers:"+a);
        });
    }

    /**
     * 返回Set集合: toSet()
     * 用于将集合累积到Set集合中，它会删除重复元素
     * @param integers
     */
    private static void collectorsToSetDemo(List<Integer> integers) {
        integers.stream().map(a->a*a).collect(Collectors.toSet()).forEach(a->{
            System.out.println("Set："+a);
        });
        int j = 0;
        integers.forEach(b->{
            System.out.println("List:"+b);
        });
    }

    /**
     * 返回List集合: toList()
     * 用于将元素累积到List集合中。它将创建一个新的List集合（不会更改当前集合【原集合】）
     * @param integers
     */
    private static void collectersToListDemo(List<Integer> integers) {
        integers.stream().map(a -> a * a).collect(Collectors.toList()).forEach(a->{
                    System.out.println("integerList-------"+a);
                });

        System.out.println("=============================");

        integers.forEach(a->{
            System.out.println("Integers==="+a);
        });
    }
}
