import React from 'react'

import {withRouter} from 'react-router'
import {Menu} from 'semantic-ui-react'
import {Link} from 'react-router-dom'


export const NavMenuItem = withRouter(({location, path, name}) =>
    <Link to={path}>
        <Menu.Item name='editorials'
                   active={location.pathname.startsWith(path)}>
            {name}
        </Menu.Item>
    </Link>)


