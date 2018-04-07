const actionCreator = type => data => ({type, data})

export const RENDER = 'redraw'

export const SELECT = 'select-node'

export const CLEAR_SELECTION = 'clear-selection'

export const SWITCH = 'switch-nodes'

export const render = actionCreator(RENDER)

export const select = actionCreator(SELECT)

export const clearSelection = actionCreator(CLEAR_SELECTION)

export const switchNodes = actionCreator(SWITCH)


