import React from 'react'

import Tree from 'src/components/tree/Tree'
import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'
import List from 'src/components/list/List'

const list = true
const tree = false

const h = heap()

range(1, 10).forEach(v => h.insert(v))

const App = () =>
    <div className="app">
        {tree && <Tree/>}
        {list && <List/>}
    </div>

export default App