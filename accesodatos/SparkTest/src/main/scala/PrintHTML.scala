import com.coxautodata.vegalite4s.VegaLite
import com.coxautodata.vegalite4s.renderers.HTMLRenderer

import java.io.{File, FileWriter}

class PrintHTML {


  def pintar(values: String): Unit = {

    val renderer = HTMLRenderer(h => {
      val fileWriter = new FileWriter(new File("hello.html"))
      fileWriter.write(h)
      fileWriter.close()

    }, true)

    VegaLite()
      .withObject(
        s"""
  {
  "$$schema": "https://vega.github.io/schema/vega-lite/v3.json",
  "description": "A simple bar chart with embedded data.",
  "data": {
    "values": $values
  },
  "mark": "bar",
  "encoding": {
    "x": {"field": "a", "type": "ordinal"},
    "y": {"field": "b", "type": "quantitative"}
  }
  }""").show(renderer)

  }

}
