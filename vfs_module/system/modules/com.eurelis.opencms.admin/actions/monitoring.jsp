<%@page buffer="none" session="true" taglibs="c,cms,fn,fmt" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<% int frequencyInMillis = 5000; %>
<html>
  <head>
    <script type="text/javascript" src="<cms:link>/system/modules/com.arkema.galaxy.opencms.core/resources/js/libs/jquery-1.8.3.min.js</cms:link>"></script>
    <script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript" src="http://code.highcharts.com/stock/highstock.js"></script>
    <script type="text/javascript" src="http://code.highcharts.com/stock/modules/exporting.js"></script>
    <script type="text/javascript">
    $(function() {
      Highcharts.setOptions({
        global : { useUTC : true }
      }); 
      
      function updateInfo() {
        $.getJSON('getSystemInfo.json', function(data) {
          var time = (new Date()).getTime();
          var $system = data.system;
          var heapMax = $system.memory.heap.max;
          var heapTotal = $system.memory.heap.total;
          var heapUsed = heapTotal - $system.memory.heap.free;
          var totalSwapMemory = $system.memory.swap.total;
          var usedSwapMemory = totalSwapMemory - $system.memory.swap.free;
          $('#osName').html($system.os);
          $('#numCpus').html($system.cpu.number); 
          $('#uptime').html(Highcharts.dateFormat('%H:%M:%S', $system.jvm.uptime));         
          $('#swap-ram').html(Math.round(usedSwapMemory*10000/totalSwapMemory)/100+'% ('+Math.round(usedSwapMemory*100/1024/1024)/100+' / '+Math.round(totalSwapMemory*100/1024/1024)/100+' Mb)');
          $('#physical-ram').html(Math.round(heapUsed*10000/heapTotal)/100+'% ('+Math.round(heapUsed*100/1024/1024)/100+' / '+Math.round(heapTotal*100/1024/1024)/100+' Mb)');
          window.chartHeap.series[0].addPoint([time, heapMax], true, true, true);
          window.chartHeap.series[1].addPoint([time, heapTotal], true, true, true);
          window.chartHeap.series[2].addPoint([time, heapUsed], true, true, true);
          window.chartCpu.series[0].addPoint([time, $system.cpu.usage], true, true, true);
          window.chartThreads.series[0].addPoint([time, $system.threads.counts.total], true, true, true);
          window.chartThreads.series[1].addPoint([time, $system.threads.counts.daemon], true, true, true);
          window.chartPerm.series[0].addPoint([time, $system.memory.perm.max], true, true, true);
          window.chartPerm.series[1].addPoint([time, $system.memory.perm.committed], true, true, true);
          window.chartPerm.series[2].addPoint([time, $system.memory.perm.used], true, true, true);
          window.chartOld.series[0].addPoint([time, $system.memory.old.max], true, true, true);
          window.chartOld.series[1].addPoint([time, $system.memory.old.committed], true, true, true);
          window.chartOld.series[2].addPoint([time, $system.memory.old.used], true, true, true);
          window.chartEden.series[0].addPoint([time, $system.memory.eden.max], true, true, true);
          window.chartEden.series[1].addPoint([time, $system.memory.eden.committed], true, true, true);
          window.chartEden.series[2].addPoint([time, $system.memory.eden.used], true, true, true);
          window.chartSurvivor.series[0].addPoint([time, $system.memory.survivor.max], true, true, true);
          window.chartSurvivor.series[1].addPoint([time, $system.memory.survivor.committed], true, true, true);
          window.chartSurvivor.series[2].addPoint([time, $system.memory.survivor.used], true, true, true);
        });
      }
      
      window.chartCpu = new Highcharts.StockChart({
        chart : {
          renderTo : 'cpu',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y)+' %</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: {
          buttons: [
            { count: 1, type: 'minute', text: '1m' },
            { count: 5, type: 'minute', text: '5m' }, 
            { count: 30, type: 'minute', text: '½h' }, 
            { count: 1, type: 'hour', text: '1h' }, 
            { count: 2, type: 'hour', text: '2h' }
          ],
          inputEnabled: false,
          selected: 0
        },
        exporting: { enabled: true },
        title : {
          text : 'CPU Usage'
        },
        series : [ 
          {type : 'spline', name : 'CPU Usage', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
    
      window.chartHeap = new Highcharts.StockChart({
        chart : {
          renderTo : 'ram',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y/1024/1024)+' Mb</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: {
          buttons: [
            { count: 1, type: 'minute', text: '1m' },
            { count: 5, type: 'minute', text: '5m' }, 
            { count: 30, type: 'minute', text: '½h' }, 
            { count: 1, type: 'hour', text: '1h' }, 
            { count: 2, type: 'hour', text: '2h' }
          ],
          inputEnabled: false,
          selected: 0
        },
        exporting: { enabled: true },
        title : {
          text : 'Heap'
        },
        series : [ 
          {type : 'area', name : 'Max Memory', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Size Memory', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Used Memory', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
      
    
      window.chartThreads = new Highcharts.StockChart({
        chart : {
          renderTo : 'threads',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y)+'</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: {
          buttons: [
            { count: 1, type: 'minute', text: '1m' },
            { count: 5, type: 'minute', text: '5m' }, 
            { count: 30, type: 'minute', text: '½h' }, 
            { count: 1, type: 'hour', text: '1h' }, 
            { count: 2, type: 'hour', text: '2h' }
          ],
          inputEnabled: false,
          selected: 0
        },
        exporting: { enabled: true },
        title : {
          text : 'Threads'
        },
        series : [ 
          {type : 'line', name : 'Live threads', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'line', name : 'Daemon threads', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
      
      window.chartPerm = new Highcharts.StockChart({
        chart : {
          renderTo : 'perm',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y/1024/1024)+' Mb</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: { enabled: false },
        exporting: { enabled: false },
        navigator: { enabled: false },
        title : {
          text : 'Perm Gen'
        },
        series : [ 
          {type : 'area', name : 'Max', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}, 
          {type : 'area', name : 'Total', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Used', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
      
      window.chartOld = new Highcharts.StockChart({
        chart : {
          renderTo : 'old',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y/1024/1024)+' Mb</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: { enabled: false },
        exporting: { enabled: false },
        navigator: { enabled: false },
        title : {
          text : 'Old Gen'
        },
        series : [
          {type : 'area', name : 'Max', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},  
          {type : 'area', name : 'Total', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Used', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
      
      window.chartEden = new Highcharts.StockChart({
        chart : {
          renderTo : 'eden',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y/1024/1024)+' Mb</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: { enabled: false },
        exporting: { enabled: false },
        navigator: { enabled: false },
        title : {
          text : 'Eden Space'
        },
        series : [
          {type : 'area', name : 'Max', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},  
          {type : 'area', name : 'Total', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Used', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
      
      window.chartSurvivor = new Highcharts.StockChart({
        chart : {
          renderTo : 'survivor',
          events : {
            load : function() {
              setInterval(updateInfo, <%=frequencyInMillis%>);
            }
          }
        },
        credits: false,
        legend: {
          layout: 'vertical',
          enabled: true,
          align: 'right',
          verticalAlign: 'top',
          x: -10,
          y: 100,
          borderWidth: 1
        }, 
        tooltip: { 
          style: { padding: '10px' }, 
          valueDecimals : 2,
          formatter:function() {
            var s = '<b>Time: ' + Highcharts.dateFormat('%Y/%m/%d %H:%M:%S', this.x) + '</b><br/>';
            $.each(this.points, function(i, point) {
              s += '<span style="color:'+this.series.color+';font-weight:bold">'+this.series.name+'</span>:<b>'+Math.round(this.point.y/1024/1024)+' Mb</b><br/>';
            });
            return s;
          },
        },
        rangeSelector: { enabled: false },
        exporting: { enabled: false },
        navigator: { enabled: false },
        title : {
          text : 'Survivor'
        },
        series : [
          {type : 'area', name : 'Max', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},  
          {type : 'area', name : 'Total', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()},
          {type : 'area', name : 'Used', data: (function(){var data=[],time=(new Date()).getTime(),i;for(i=-999;i<=0;i++){data.push([time+i*1000,0]);};return data;})()}
        ]        
      });
    });
    </script>
  </head>
  <body>
    <h1>Monitoring page</h1>
    <table>
      <tr>
        <td><b>Operating System</b></td>
        <td id="osName"></td>
      </tr>
      <tr>
        <td><b>Tomcat uptime</b></td>
        <td id="uptime"></td>
      </tr>
      <tr>
        <td><b>Number of CPUs</b></td>
        <td id="numCpus"></td>
      </tr>
      <tr>
        <td><b>Physical RAM</b></td>
        <td><span id="physical-ram"/></td>
      </tr>
      <tr>
        <td><b>Swap RAM</b></td>
        <td><span id="swap-ram"/></td>
      </tr>
    </table>
    <br/>
    
    <table>
      <tr>
        <td><div id="cpu" style="height: 300px; width: 500px"></div></td>
        <td><div id="ram" style="height: 300px; width: 500px"></div></td>
      </tr>
      <tr>
        <td> </td>
        <td><div id="threads" style="height: 300px; width: 500px"></div></td>
      </tr>
      <tr>
        <td><div id="perm" style="height: 300px; width: 500px"></div></td>
        <td><div id="old" style="height: 300px; width: 500px"></div></td>
      </tr>
      <tr>
        <td><div id="eden" style="height: 300px; width: 500px"></div></td>
        <td><div id="survivor" style="height: 300px; width: 500px"></div></td>
      </tr>
    </table>
      
  </body>
</html>