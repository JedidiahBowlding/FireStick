import express from 'express';
import OpenAI from 'openai';
import { z } from 'zod';

const app=express();
app.use(express.json({limit:'64kb'}));
const Card=z.object({title:z.string().max(120),tag:z.string().max(40),body:z.string().max(400),x:z.number(),y:z.number()});
const Request=z.object({cards:z.array(Card).min(1).max(60)});
app.get('/health',(_,res)=>res.json({ok:true,mode:process.env.OPENAI_API_KEY?'live':'demo'}));
app.post('/organize',async(req,res)=>{
  const parsed=Request.safeParse(req.body); if(!parsed.success)return res.status(400).json({error:'Invalid canvas'});
  if(!process.env.OPENAI_API_KEY)return res.status(503).json({error:'OPENAI_API_KEY is not configured'});
  try{
    const client=new OpenAI();
    const compact=parsed.data.cards.map(({title,body})=>({title,body}));
    const response=await client.responses.create({
      model:process.env.OPENAI_MODEL||'gpt-5-mini',
      instructions:'Organize this TV canvas into one goal and concise priorities. Preserve card count and core meaning. Return JSON only.',
      input:JSON.stringify(compact), max_output_tokens:800,
      text:{format:{type:'json_schema',name:'organized_canvas',strict:true,schema:{type:'object',properties:{cards:{type:'array',items:{type:'object',properties:{title:{type:'string'},tag:{type:'string'},body:{type:'string'}},required:['title','tag','body'],additionalProperties:false}}},required:['cards'],additionalProperties:false}}}
    });
    const result=JSON.parse(response.output_text);
    const cards=result.cards.slice(0,parsed.data.cards.length).map((c,i)=>({...parsed.data.cards[i],...c}));
    res.json({cards,usage:response.usage,request_id:response.id});
  }catch(error){console.error(error);res.status(502).json({error:'AI transformation failed'});}
});
app.listen(process.env.PORT||8787,()=>console.log(`Fire Canvas backend on :${process.env.PORT||8787}`));
