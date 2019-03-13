export const link = (n1, n2, { value = 20, id } = {}) => ({
    source: n1.id,
    target: n2.id,
    id: id || n1.id + '#' + n2.id,
    value
})
