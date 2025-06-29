package pers.zkx.algo.base.sorts;
/**
 * @author: zhangkuixing
 * @date: 2025/6/29 18:56
 */
public class QuickSortTest extends SortAlgoApiTest {
    SortAlgoApi sortAlgoApi = new QuickSort();

    @Override
    SortAlgoApi getSortAlgoApi() {
        return sortAlgoApi;
    }
}
