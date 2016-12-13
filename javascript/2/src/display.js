import assert from 'assert'

export default class Display {

    constructor(canvas, scale = 1) {
        this.canvas = canvas
        this.ctx = canvas.getContext('2d')

        this.colWidth = 10
        this.spacing = 5
        this.color = '#C33632'
        this.selectedColor = '#5F5F5F'
        this.examinedColor = '#C0C0C0'
        this.scale = scale

    }

    /**
     * ============ Public API
     */


    /**
     *
     * @param array
     */

    setArrayModel(model) {
        assert(!this.array)

        this.array = model
        this.drawArray()
    }


    /**
     * =========== Private methods
     */

    drawArray() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.array.forEach((el, i) => this.draw(el, i))
        window.requestAnimationFrame(() => this.drawArray())
    }

    draw(el, index) {
        var color = el.selected ? this.selectedColor : this.color
        if (el.examined)
            color = this.examinedColor

        this.ctx.fillStyle = color


        const x = this.spacing + (this.colWidth + this.spacing) * index
        const y = 0
        this.ctx.fillRect(x, this.canvas.height - el.val * this.scale,
            this.colWidth, this.canvas.height);
    }

}