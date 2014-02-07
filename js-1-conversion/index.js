
var Conversion = (function()
{
	var filename = "data.json";
	var json;
	
	//Function to set the result of the algorithm
	var set_purchases_visits = function(visits,purchases)
	{
		$('span[data-value="visits"]').first().html(visits);
		$('span[data-value="purchases"]').html(purchases);
		$('span[data-value="visits"]').last().html((purchases/visits).toFixed(4)+" %"); 
	};
	
	// Function to call the search algorithm with the specific OS and Brand
	var set_new_values = function(os, brand)
	{
		var values = {"visits" : 0, "purchases" : 0, "found": false};
		
		if (os == "All"){
			search_json(json, values, 0, brand, null);	
			set_purchases_visits(values["visits"],values["purchases"]);
		}else{
			search_json(json[os], values, 1, brand, null);
			set_purchases_visits(values["visits"],values["purchases"]);
		}
	}
	
	// Init change event in both selects
	var change_event = function()
	{
		$('select[name="os"]').on('change', function(){
			$('select[name="brand"]').find('option').remove();
			$('select[name="brand"]').append($("<option />").val("All").text("All"));
			set_new_values(this.value, $('select[name="brand"] option:selected').text());
		});
		
		$('select[name="brand"]').on('change', function(){
			set_new_values($('select[name="os"] option:selected').text(), this.value);
		});
	};
	
	// Algorithm Search json
	// - result: json array
	// - values: array with visits and purchases to do the sum recursive
	// - iteration: if it is 0 the algorithm will add the OS into select OS (init call)
	//              if it is 1 the algorithm will add the BRAND into select BRAND
	// - condition: if we are looking for any specific brand
	// - lastkey: to keep the last key along the recursive call because the condition could be in the las level of the recursion
	var search_json = function(result, values, iteration, condition, lastkey)
	{
		$.each(result, function(key,value){
			if (iteration == 0){
				if ($('select[name="os"] option[value="'+key+'"]').length == 0) 
					$('select[name="os"]').append($("<option />").val(key).text(key));
			}
			
			if (typeof value == "object"){
					if (iteration == 1){
						if ($('select[name="brand"] option[value="'+key+'"]').length == 0) 
							$('select[name="brand"]').append($("<option />").val(key).text(key));
					}	
					
					if (lastkey == condition)
						values["found"] = true;
					
					search_json(value, values, iteration+1, condition, key); // Recursive call 
					
					values["found"] = false;
			}else{
				if ((lastkey == condition) || (condition == "All") || (values["found"] == true)){
					if (key == "visits")
						values["visits"] += value;
					else
						values["purchases"] += value;
				}				
			}		
		});
	};
	
	// Read json and load datas within select
	var init_select = function()
	{
		$.getJSON(filename, function(result)
		{						
			var values = {"visits" : 0, "purchases" : 0, "found": false};
			
			$('select[name="os"]').append($("<option />").val("All").text("All"));
			$('select[name="brand"]').append($("<option />").val("All").text("All"));
			
			search_json(result, values, 0, "All", null);
			
			set_purchases_visits(values["visits"],values["purchases"]);
			
			return json = result;
		});
	};
	
	var start = function()
	{
		init_select();
		change_event();
	};
	
	return {
		'ignition':start
	}
})();

$(function(){ /* DOM ready */
	Conversion.ignition();
});
