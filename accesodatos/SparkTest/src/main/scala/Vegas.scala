import com.coxautodata.vegalite4s.renderers.HTMLRenderer
import com.coxautodata.vegalite4s.renderers.ImplicitRenderers.HTMLPagePrintlnRenderer

import java.io.{File, FileWriter}

object Vegas extends App{
  import com.coxautodata.vegalite4s.VegaLite
  import com.coxautodata.vegalite4s.renderers.ImplicitRenderers.AutoSelectionRenderer

  val renderer2 = HTMLRenderer(h => {
    val fileWriter = new FileWriter(new File("hello.html"))
    fileWriter.write(h)
    fileWriter.close()
    //println(h)
  }, true)





  VegaLite()
    .withObject("""
  {
  "$schema": "https://vega.github.io/schema/vega-lite/v3.json",
  "description": "A simple bar chart with embedded data.",
  "data": {
    "values": [
      {"a": "A","b": 28}, {"a": "B","b": 55}, {"a": "C","b": 43},
      {"a": "D","b": 91}, {"a": "E","b": 81}, {"a": "F","b": 53},
      {"a": "G","b": 19}, {"a": "H","b": 87}, {"a": "I","b": 52}
    ]
  },
  "mark": "bar",
  "encoding": {
    "x": {"field": "a", "type": "ordinal"},
    "y": {"field": "b", "type": "quantitative"}
  }
  }""").show(renderer2)


}
