const actionCreator = type => (data, opts = {}) => ({type, data, ...opts})

export const RENDER = 'redraw'

export const SELECT = 'select-node'

export const CLEAR_SELECTION = 'clear-sel'

export const SWITCH = 'switch-nodes'

export const render = actionCreator(RENDER)

export const select = actionCreator(SELECT)

export const clearSelection = actionCreator(CLEAR_SELECTION)

export const switchNodes = actionCreator(SWITCH)


