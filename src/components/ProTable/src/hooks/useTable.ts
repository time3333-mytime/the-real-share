import { Table } from '../types'
import { reactive, computed, onMounted, toRefs } from 'vue'

/**
 * @description table 页面操作方法封装
 * @param {Function} api 获取表格数据 api 方法(必传)
 * @param {Object} initParam 获取数据初始化参数(非必传，默认为{})
 * @param {Boolean} isPageable 是否有分页(非必传，默认为true)
 * @param {Function} dataCallBack 对后台返回的数据进行处理的方法(非必传)
 * */
export const useTable = (
  api: (params: any) => Promise<any>,
  initParam: object = {},
  isPageable = true,
  dataCallBack?: (data: any) => any,
) => {
  const state = reactive<Table.TableStateProps>({
    tableData: [],
    pageable: {
      pageNum: 1,
      pageSize: 10,
      total: 0,
    },
    searchParam: {},
    searchInitParam: {},
    totalParam: {},
    loading: false,
  })

  const pageParam = computed(() => ({
    pageNum: state.pageable.pageNum,
    pageSize: state.pageable.pageSize,
  }))

  onMounted(() => {
    reset()
  })

  const getTableList = async () => {
    try {
      state.loading = true
      Object.assign(
        state.totalParam,
        initParam,
        isPageable ? pageParam.value : {},
      )
      let { data } = await api(state.totalParam)
      state.loading = false
      dataCallBack && (data = dataCallBack(data))

      state.tableData = isPageable ? data.list : data
      const { pageNum, pageSize, total } = data
      const page = pageNum ?? state.pageable.pageNum
      const limit = pageSize ?? state.pageable.pageSize
      isPageable && updatePageable({ pageNum: page, pageSize: limit, total })
    } catch (error) {
      state.loading = false
      console.error('获取表格数据出错:', error)
    }
  }

  const updatedTotalParam = () => {
    state.totalParam = {}
    const nowSearchParam: { [key: string]: any } = {}
    for (const key in state.searchParam) {
      if (
        state.searchParam[key] ||
        state.searchParam[key] === false ||
        state.searchParam[key] === 0
      ) {
        nowSearchParam[key] = state.searchParam[key]
      }
    }
    Object.assign(
      state.totalParam,
      initParam,
      nowSearchParam,
      isPageable ? pageParam.value : {},
    )
  }

  const updatePageable = (resPageable: Table.Pageable) => {
    Object.assign(state.pageable, resPageable)
  }

  const search = () => {
    state.pageable.pageNum = 1
    updatedTotalParam()
    getTableList()
  }

  const reset = () => {
    state.pageable.pageNum = 1
    state.searchParam = {}
    Object.keys(state.searchInitParam).forEach((key) => {
      state.searchParam[key] = state.searchInitParam[key]
    })
    updatedTotalParam()
    getTableList()
  }

  const handleSizeChange = (val: number) => {
    state.pageable.pageNum = 1
    state.pageable.pageSize = val
    getTableList()
  }

  const handleCurrentChange = (val: number) => {
    state.pageable.pageNum = val
    getTableList()
  }

  return {
    ...toRefs(state),
    getTableList,
    search,
    reset,
    handleSizeChange,
    handleCurrentChange,
  }
}
