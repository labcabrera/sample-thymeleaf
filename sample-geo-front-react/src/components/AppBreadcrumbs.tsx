import React, { ReactNode } from "react";
import { Stack, Breadcrumbs, Link, Typography } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

type BreadcrumbItem = {
  label: string;
  href?: string;
};

type Props = {
  items: BreadcrumbItem[];
  children?: ReactNode;
};

export default function AppBreadcrumbs({ items, children }: Props) {
  return (
    <Stack
      direction="row"
      sx={{
        justifyContent: "space-between",
      }}
    >
      <Breadcrumbs aria-label="breadcrumb">
        {items.map((it, idx) =>
          it.href ? (
            <Link
              key={idx}
              component={RouterLink}
              underline="hover"
              color={it.href ? "primary" : "inherit"}
              to={it.href}
            >
              {it.label}
            </Link>
          ) : (
            <Typography key={idx} color="text.primary">
              {it.label}
            </Typography>
          ),
        )}
      </Breadcrumbs>
      <>{children}</>
    </Stack>
  );
}
