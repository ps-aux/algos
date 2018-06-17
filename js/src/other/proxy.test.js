describe('proxy', () => {
    const a = [2, 4]
    const p = new Proxy(a, {
        get: (o, a) => 'val is ' + o[a]
    })

    it('intercept get', () => {
        expect(p[1]).toBe(`val is ${a[1]}`)

    })
})