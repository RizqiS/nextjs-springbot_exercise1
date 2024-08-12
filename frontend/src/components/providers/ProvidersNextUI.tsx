"use client"

import React, { ReactNode } from 'react'
import {NextUIProvider} from "@nextui-org/react";
export default function ProvidersNextUI({children} : {children: Readonly<ReactNode>}) {
  return (
    <NextUIProvider>
      {children}
    </NextUIProvider>
  )
}
