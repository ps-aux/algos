import React from 'react'

import {heap} from 'src/domain/data-structures/heap'
import {range} from 'ramda'
import View from 'src/components/basic/View'
import Bst from 'src/domain/algorithms/tree/impl/BstComp'
import {Menu} from 'semantic-ui-react'
import Page from 'src/components/Page'
import {NavMenuItem} from 'src/components/NavMenu'
import Sorting from 'src/domain/algorithms/sorting/components/Sorting'
import Graph from 'src/domain/algorithms/graph/components/Graph'


const h = heap()

range(1, 10).forEach(v => h.insert(v))

const pages = [
    {
        path: '/sorting',
        title: 'Sorting',
        component: Sorting
    },
    {
        path: '/Bst',
        title: 'Binary search tree',
        component: Bst
    },
    {
        path: '/graph/:type?',
        link: '/graph/',
        title: 'Graph',
        component: Graph
    }
]

const App = () =>
    <View className="app">
        <Menu fixed="top">
            {pages.map(({title, path, link}) =>
                <NavMenuItem name={title}
                             key={link || path}
                             path={link || path}/>)}
        </Menu>
        {pages.map(p => <Page
            key={p.path}
            {...p}/>)}
    </View>

export default App


