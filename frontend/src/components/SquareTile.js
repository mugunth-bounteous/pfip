import { Button, Card, CardActions, CardContent, CardHeader, CardMedia } from '@mui/material'
import React from 'react'
import { Link } from 'react-router-dom'

const SquareTile = ( {courseId,name} ) => {
  return (
    <div>
        <Card sx={
          {height:150,width:150,alignItems:'center',justifyContent:'center',display:'flex'}
        }>
            <CardContent sx={{fontWeight:'bold'}}>
              {name}
            </CardContent>
        </Card>
    </div>
  )
}

export default SquareTile