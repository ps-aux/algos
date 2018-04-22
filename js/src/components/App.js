import React from 'react'

import Tree from 'src/components/tree/Tree'
import ArraySort from 'src/components/ArraySort'
import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'

const sort = false
const tree = true

const h = heap()

range(1, 10).forEach(v => h.insert(v))

const App = () =>
    <div className="app">
        {tree && <Tree/>}
        {sort && <ArraySort/>}
    </div>

export default App